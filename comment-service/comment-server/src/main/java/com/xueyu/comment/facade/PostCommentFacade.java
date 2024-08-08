package com.xueyu.comment.facade;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.comment.exception.CommentException;
import com.xueyu.comment.facade.request.PostCommentReq;
import com.xueyu.comment.mapper.LikeMapper;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.enums.CommentType;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.pojo.vo.CommentAnswerVO;
import com.xueyu.comment.pojo.vo.CommentPostVO;
import com.xueyu.common.web.facade.FacadeStrategy;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 使用 获取的评论列表，和userId，返回评论区响应体
 * 参数；
 * userId 当前操作用户id
 * List<Comment> 原评论列表数据
 * @author durance
 */
@Component
@Slf4j
public class PostCommentFacade implements FacadeStrategy<PostCommentReq, List<CommentPostVO>> {

    @Resource
    UserClient userClient;

    @Resource
    LikeMapper likeMapper;

    @Override
    public List<CommentPostVO> execBusiness(PostCommentReq postCommentReq) {
        Integer userId = postCommentReq.getUserId();
        List<Comment> comments = postCommentReq.getComments();
        // 评论id集合
        List<Integer> commentIdList = new ArrayList<>();
        // 统计用户id，并进行去重,同时统计commentId
        Set<Integer> userIdSet = new HashSet<>();
        for (Comment comment : comments) {
            userIdSet.add(comment.getUserId());
            commentIdList.add(comment.getId());
        }
        List<Integer> userIds = new ArrayList<>(userIdSet);
        // 查询出所有有关的用户信息
        Map<Integer, UserSimpleVO> userInfo = userClient.getUserDeatilInfoMap(userIds).getData();
        List<CommentPostVO> rootComment = new LinkedList<>();
        // 创建关联map，key为根id，值为 子评论集合
        Map<Integer, List<CommentAnswerVO>> answerCommentMap = new HashMap<>(16);
        Map<CommentPostVO, Comment> linkMap = new HashMap<>(16);
        //创建点赞映射
        Map<Integer, Like> likeMap = new HashMap<>();
        if(userId!=null){
            //获取用户点赞信息
            LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Like::getUserId,userId)
                    .in(Like::getCommentId,commentIdList);
            List<Like> likeList = likeMapper.selectList(queryWrapper);
            //点赞数据存入map集合
            for(Like like : likeList){
                likeMap.put(like.getCommentId(),like);
            }
        }
        for (Comment comment : comments) {
            // 倒序存入根评论，正序存入回复
            if (comment.getType().equals(CommentType.ROOT.getValue())) {
                CommentPostVO commentPostVO = new CommentPostVO();
                BeanUtils.copyProperties(comment, commentPostVO);
                // 设置用户信息
                commentPostVO.setUserInfo(userInfo.get(comment.getUserId()));
                // 设置是否点赞信息
                if(userId!=null){
                    commentPostVO.setIsLike(likeMap.containsKey(comment.getId()));
                }
                // 从头部插入根评论，并创建map
                rootComment.add(0, commentPostVO);
                answerCommentMap.put(comment.getRootId(), new ArrayList<>());
                linkMap.put(commentPostVO, comment);
            } else {
                // 时间排序的集合可以保证已经创建了根评论的map
                List<CommentAnswerVO> commentAnswerVOList = answerCommentMap.get(comment.getRootId());
                CommentAnswerVO commentAnswerVO = new CommentAnswerVO();
                try{
                    BeanUtils.copyProperties(comment, commentAnswerVO);
                    // 设置用户信息和回复用户信息
                    commentAnswerVO.setUserInfo(userInfo.get(comment.getUserId()));
                    commentAnswerVO.setAnswerUserInfo(userInfo.get(comment.getToUserId()));
                    // 设置是否点赞信息
                    commentAnswerVO.setIsLike(likeMap.containsKey(comment.getId()));
                    commentAnswerVOList.add(commentAnswerVO);
                }catch (NullPointerException e){
                    log.error("可能出现创建时间混乱或rootId混乱, 当前子评论 id ->{}, 根评论 id ->{}", commentAnswerVO.getId(), commentAnswerVO.getRootId());
                    throw new CommentException("该帖子可能出现创建时间混乱或rootId混乱");
                }
            }
        }
        // 第二次遍历将子评论赋值到根评论
        for (Map.Entry<CommentPostVO, Comment> entry : linkMap.entrySet()) {
            entry.getKey().setAnswerCommentList(answerCommentMap.get(entry.getValue().getRootId()));
        }
        return rootComment;
    }
}
