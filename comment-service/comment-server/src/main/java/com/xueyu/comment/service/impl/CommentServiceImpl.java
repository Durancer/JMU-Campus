package com.xueyu.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.comment.exception.CommentException;
import com.xueyu.comment.facade.PostCommentFacade;
import com.xueyu.comment.facade.request.PostCommentReq;
import com.xueyu.comment.mapper.CommentMapper;
import com.xueyu.comment.mapper.LikeMapper;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.enums.CommentStatusEnum;
import com.xueyu.comment.pojo.enums.CommentType;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.comment.sdk.vo.CommentPostVO;
import com.xueyu.comment.request.CommentQueryRequest;
import com.xueyu.comment.request.PostCommentQueryRequest;
import com.xueyu.comment.sdk.dto.CommentDTO;
import com.xueyu.comment.service.CommentService;
import com.xueyu.common.core.request.PageRequest;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.client.PostClient;
import com.xueyu.post.sdk.dto.PostDTO;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.xueyu.comment.sdk.constant.CommentMqContants.*;

/**
 * @author durance
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Resource
	RabbitTemplate rabbitTemplate;

	@Resource
	UserClient userClient;

	@Resource
	LikeMapper likeMapper;

	@Resource
	CommentMapper commentMapper;

	@Resource
	PostClient postClient;

	@Resource
	PostCommentFacade postCommentFacade;

	@Override
	public Boolean sendUserComment(Comment comment) {
		log.info("发送评论报文 -> {}", comment);
		PostDTO postInfo = postClient.getPostInfo(comment.getPostId()).getData();
		if (postInfo == null){
			throw new CommentException("不存在该帖子信息");
		}
		// 校验参数
		verifySendParam(comment);
		Date time = new Date();
		comment.setCreateTime(time);
		comment.setUpdateTime(time);
		query().getBaseMapper().insert(comment);
		// 如果为根评论则设置rootId为本身id
		if (comment.getType().equals(CommentType.ROOT.getValue())) {
			comment.setRootId(comment.getId());
			updateById(comment);
		}
		// 发送mq消息
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setCommentId(comment.getId());
		commentDTO.setPostId(comment.getPostId());
		commentDTO.setUserId(comment.getUserId());
		commentDTO.setAuthorId(postInfo.getUserId());
		rabbitTemplate.convertAndSend(COMMENT_EXCHANGE, COMMENT_INSERT_KEY, commentDTO);
		return true;
	}

	private void verifySendParam(Comment comment){
		if (comment.getHot() != null){
			throw new CommentException("非法字段传入");
		}
		if (!(comment.getType().equals(CommentType.ROOT.getValue()) || comment.getType().equals(CommentType.ANSWER.getValue()))) {
			throw new CommentException("错误的评论类型");
		}
		if (comment.getType().equals(CommentType.ANSWER.getValue()) && comment.getToUserId() == null){
			throw new CommentException("请选择回复对象");
		}
	}

	@Override
	public Boolean deleteUserComment(Integer userId, Integer commentId) {
		log.info("用户id -> {} 执行操作删除 评论 id -> {}", userId, commentId);
		Comment comment = lambdaQuery().eq(Comment::getId, commentId).eq(Comment::getUserId, userId).one();
		if (comment == null) {
			throw new CommentException("评论与用户不匹配");
		}
		// 如果为根评论，需要连同回复评论一起删除，否则仅删除该条评论即可
		// todo 连同回复的评论一起删除时，可能涉及多个用户的总评论数减少，这一点还没有做
		int deleteNum;
		if (comment.getType().equals(CommentType.ROOT.getValue())) {
			LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
			wrapper.eq(Comment::getRootId, commentId);
			List<Comment> commentList = commentMapper.selectList(wrapper);
			deleteNum = query().getBaseMapper().delete(wrapper);
			List<Integer> ids = commentList.stream().map(Comment::getId).collect(Collectors.toList());
			LambdaQueryWrapper<Like> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.in(Like::getCommentId, ids);
			likeMapper.delete(lambdaQueryWrapper);
			log.info("根评论 id -> {} 被删除，连同子评论，共删除 {} 条评论", commentId, deleteNum);
		} else {
			deleteNum = query().getBaseMapper().deleteById(commentId);
			LambdaQueryWrapper<Like> likeLambdaQueryWrapper = new LambdaQueryWrapper<>();
			likeLambdaQueryWrapper.eq(Like::getCommentId, commentId);
			likeMapper.delete(likeLambdaQueryWrapper);
			log.info("子评论 id -> {} 被删除", commentId);
		}
		// 发送mq消息
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setEffectNum(deleteNum);
		commentDTO.setUserId(userId);
		commentDTO.setPostId(comment.getPostId());
		commentDTO.setCommentId(commentId);
		rabbitTemplate.convertAndSend(COMMENT_EXCHANGE, COMMENT_DELETE_KEY, commentDTO);
		return true;
	}

	@Override
	public ListVO<CommentPostVO> getPostComments(PostCommentQueryRequest request) {
		ListVO<CommentPostVO> result = new ListVO<>();
		// 分页查询出属于该帖子的根评论
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getPostId, request.getPostId()).orderByAsc(Comment::getCreateTime);
		wrapper.eq(Comment::getStatus, CommentStatusEnum.PUBLIC.getCode());
		wrapper.eq(Comment::getType, CommentType.ROOT.getValue());
		IPage<Comment> page = new Page<>(request.getCurrent(), request.getSize());
		query().getBaseMapper().selectPage(page, wrapper);
		BeanUtils.copyProperties(page, result);
		if (CollectionUtils.isEmpty(page.getRecords())){
			return ListVO.buildNonDataRes(request.getCurrent(), request.getSize());
		}
		List<Integer> rootIds = page.getRecords().stream().map(Comment::getId).collect(Collectors.toList());
		// 查询帖子相关的评论，包括子评论
		LambdaQueryWrapper<Comment> commentListWrapper = new LambdaQueryWrapper<>();
		commentListWrapper.in(Comment::getRootId, rootIds);
		wrapper.eq(Comment::getStatus, CommentStatusEnum.PUBLIC.getCode());
		List<Comment> comments = query().getBaseMapper().selectList(commentListWrapper);
		PostCommentReq postCommentReq = new PostCommentReq();
		postCommentReq.setComments(comments);
		postCommentReq.setUserId(request.getUserId());
		// 将列表交由facede处理顺序和归属问题
		List<CommentPostVO> commentPostList = postCommentFacade.execBusiness(postCommentReq);
		result.setRecords(commentPostList);
		return result;
	}

	@Override
	public ListVO<CommentAnswerVO> getUserSelfComments(Integer userId, PageRequest request) {
		ListVO<CommentAnswerVO> res = new ListVO<>();
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getUserId, userId);
		IPage<Comment> page = new Page<>(request.getCurrent(), request.getSize());
		query().getBaseMapper().selectPage(page, wrapper);
		BeanUtils.copyProperties(page, res);
		List<CommentAnswerVO> commentAnswerVOS = commentConvertAnswerVO(page.getRecords());
		res.setRecords(commentAnswerVOS);
		return res;
	}

	@Override
	public ListVO<CommentAnswerVO> getOtherUserComments(Integer userId, PageRequest request) {
		ListVO<CommentAnswerVO> res = new ListVO<>();
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Comment::getUserId, userId);
		wrapper.eq(Comment::getStatus, CommentStatusEnum.PUBLIC.getCode());
		IPage<Comment> page = new Page<>(request.getCurrent(), request.getSize());
		query().getBaseMapper().selectPage(page, wrapper);
		BeanUtils.copyProperties(page, res);
		List<CommentAnswerVO> commentAnswerVOS = commentConvertAnswerVO(page.getRecords());
		res.setRecords(commentAnswerVOS);
		return res;
	}

	@Override
	public ListVO<CommentAnswerVO> getUserAnsweredComments(Integer toUserId, PageRequest request) {
		ListVO<CommentAnswerVO> res = new ListVO<>();
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		// 查询收到得到信息，且不为自己发送的
		wrapper.eq(Comment::getToUserId, toUserId).ne(Comment::getUserId, toUserId);
		IPage<Comment> page = new Page<>(request.getCurrent(), request.getSize());
		query().getBaseMapper().selectPage(page, wrapper);
		BeanUtils.copyProperties(page, res);
		List<CommentAnswerVO> commentAnswerVOS = commentConvertAnswerVO(page.getRecords());
		res.setRecords(commentAnswerVOS);
		return res;
	}

	@Override
	public void updateComment(Comment comment) {
		Comment old = query().getBaseMapper().selectById(comment.getId());
		if(!old.getUserId().equals(comment.getUserId())){
			throw new CommentException("评论id与用户id不匹配");
		}
		comment.setUpdateTime(new Date());
		int i = update().getBaseMapper().updateById(comment);
		if (i != 0){
			throw new CommentException("更新异常");
		}
	}

	@Override
	public List<CommentAnswerVO> commentConvertAnswerVO(List<Comment> commentList) {
		// 统计关联的用户id
		Set<Integer> userIdSet = commentList.stream()
				.flatMap(comment -> Stream.of(comment.getUserId(), comment.getToUserId())).collect(Collectors.toSet());
		Map<Integer, UserSimpleVO> userData = userClient.getUserDeatilInfoMap(new ArrayList<>(userIdSet)).getData();
		List<CommentAnswerVO> answerVOList = new ArrayList<>();
		for (Comment comment : commentList) {
			CommentAnswerVO commentAnswerVO = new CommentAnswerVO();
			BeanUtils.copyProperties(comment, commentAnswerVO);
			commentAnswerVO.setUserInfo(userData.get(comment.getUserId()));
			commentAnswerVO.setAnswerUserInfo(userData.get(comment.getToUserId()));
			answerVOList.add(commentAnswerVO);
		}
		return answerVOList;
	}

	/**
	 * 获取各个帖子热度最高的评论
	 *
	 * @param postIds 帖子id 集合
	 * @return answerVO对象
	 */
	@Override
	public List<CommentAnswerVO> postsMaxHotComment(List<Integer> postIds) {
		// todo 做性能优化，缓存构建
		List<Comment> comments = commentMapper.selectMaxHotByPostId(postIds);
		return commentConvertAnswerVO(comments);
	}

	@Override
	public ListVO<Comment> getManageCommentListPage(CommentQueryRequest request) {
		LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
		if (Objects.nonNull(request.getRootId())){
			wrapper.eq(Comment::getRootId, request.getRootId());
		}
		if (Objects.nonNull(request.getUserId())){
			wrapper.eq(Comment::getUserId, request.getUserId());
		}
		if (Objects.nonNull(request.getPostId())){
			wrapper.eq(Comment::getPostId, request.getPostId());
		}
		if (StringUtils.isNotEmpty(request.getType())){
			wrapper.eq(Comment::getType, request.getType());
		}
		if (Objects.nonNull(request.getToUserId())){
			wrapper.eq(Comment::getToUserId, request.getToUserId());
		}
		if (Objects.nonNull(request.getStatus())){
			wrapper.eq(Comment::getStatus, request.getStatus());
		}
		if (Objects.nonNull(request.getCreateTime())){
			wrapper.ge(Comment::getCreateTime, request.getCreateTime());
		}
		if (StringUtils.isNotEmpty(request.getContent())){
			wrapper.like(Comment::getContent, request.getContent());
		}
		wrapper.orderByDesc(Comment::getCreateTime);
		IPage<Comment> page = new Page<>(request.getCurrent(), request.getSize());
		query().getBaseMapper().selectPage(page, wrapper);
		ListVO<Comment> result = new ListVO<>();
		BeanUtils.copyProperties(page, result);
		return result;
	}

	@Override
	public void passCommentContent(Integer commentId, Integer decision, String reason) {
		if (!CommentStatusEnum.isInEnums(decision)) {
			throw new CommentException("不合法的审核参数");
		}
		// 参数合法修改评论状态
		Comment comment = new Comment();
		comment.setId(commentId);
		comment.setStatus(decision);
		if (CommentStatusEnum.PUBLIC.getCode().equals(decision)) {
			comment.setReason("");
			commentMapper.updateById(comment);
			log.info("评论 id -> {}, 审核通过", commentId);
		} else if (CommentStatusEnum.EXAMINE.getCode().equals(decision)) {
			comment.setReason("");
			commentMapper.updateById(comment);
			log.info("评论 id -> {}, 重新提交审核", commentId);
		} else {
			comment.setReason(reason);
			commentMapper.updateById(comment);
			log.info("评论 id -> {}, 审核未通过, 原因 -> {}", commentId, reason);
		}
	}

}
