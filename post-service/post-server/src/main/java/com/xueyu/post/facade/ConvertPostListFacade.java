package com.xueyu.post.facade;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.comment.client.CommentClient;
import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.common.web.facade.FacadeStrategy;
import com.xueyu.post.facade.request.ConvertPostReq;
import com.xueyu.post.mapper.LikePostMapper;
import com.xueyu.post.pojo.bo.ImageAnnexView;
import com.xueyu.post.pojo.domain.LikePost;
import com.xueyu.post.pojo.domain.Topic;
import com.xueyu.post.pojo.enums.PostIsAnonymousEnum;
import com.xueyu.post.pojo.vo.PostListVO;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.pojo.vo.VoteVO;
import com.xueyu.post.service.ImageAnnexService;
import com.xueyu.post.service.TopicService;
import com.xueyu.post.service.VoteService;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.utils.UserFactory;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 根据 帖子id列表，获取帖子列表信息，解耦合方法
 * <p>
 * 对象参数：
 * postIds 帖子id
 * authors 帖子用户id， (非必传) 如未传入将再方法里进行统计。
 * 如果在统计帖子id时能够顺便统计，在方法里将跳过统计userId的流程
 * records 帖子信息列表
 *
 * @author durance
 * @return 帖子列表信息
 */
@Component
public class ConvertPostListFacade implements FacadeStrategy<ConvertPostReq, List<PostListVO>> {

    @Resource
    LikePostMapper likePostMapper;

    @Resource
    UserClient userClient;

    @Resource
    ImageAnnexService imageAnnexService;

    @Resource
    TopicService topicService;

    @Resource
    VoteService voteService;

    @Resource
    CommentClient commentClient;

    @Resource
    UserFactory userFactory;

    @Override
    public List<PostListVO> execBusiness(ConvertPostReq convertPostReq) {
        List<Integer> authors = convertPostReq.getAuthors();
        List<PostView> records = convertPostReq.getRecords();
        List<Integer> postIds = convertPostReq.getPostIds();
        // 传入的 userIds 如果不为空，无需进行再次统计，因为在外面可能提前统计好了
        if (CollectionUtils.isEmpty(authors)) {
            authors = records.stream().map(PostView::getUserId).collect(Collectors.toList());
        }
        // 创建map postId | 点赞用户id列表数据，进行批量查询出用户id数据
        Map<Integer, List<Integer>> likeUserIdsMap = new HashMap<>(records.size());
        // 查询所有帖子的点赞信息，并按照帖子id进行分配
        LambdaQueryWrapper<LikePost> likeWrapper = new LambdaQueryWrapper<>();
        likeWrapper.in(LikePost::getPostId, postIds);
        List<LikePost> likePosts = likePostMapper.selectList(likeWrapper);
        // 用户列表判断，用户是否点赞改帖子
        Set<String> likeSet = likePosts.stream().map(likePost -> getPostIsLikeKey(likePost.getPostId(), likePost.getUserId())).collect(Collectors.toSet());
        for (LikePost likePost : likePosts) {
            if (likeUserIdsMap.containsKey(likePost.getPostId())) {
                likeUserIdsMap.get(likePost.getPostId()).add(likePost.getUserId());
            } else {
                List<Integer> userIds = new ArrayList<>();
                userIds.add(likePost.getUserId());
                likeUserIdsMap.put(likePost.getPostId(), userIds);
            }
        }
        // 查询并设置帖子 用户信息
        Map<Integer, UserSimpleVO> userInfos = userClient.getUserDeatilInfoMap(authors).getData();
        // 查询所有 图片信息
        Map<Integer, List<ImageAnnexView>> postListImgs = imageAnnexService.getPostListImgs(postIds);
        // 查询所有 话题信息
        Map<Integer, List<Topic>> topicMap = topicService.getTopicByPostIds(postIds);
        // 查询帖子热评
        List<CommentAnswerVO> hotComments = commentClient.postsMaxHotComment(postIds).getData();
        Map<Integer, CommentAnswerVO> commentMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(hotComments)){
            // 有冲突的情况下选择后者，一般没有冲突
            commentMap = hotComments.stream().collect(Collectors.toMap(
                            CommentAnswerVO::getPostId,
                            Function.identity(),
                            (existsOne, replaceOne) -> replaceOne));
        }
        return convertToPostListVO(records, userInfos, postListImgs, topicMap, likeUserIdsMap, commentMap, likeSet, convertPostReq.getUserId());
    }

    /**
     * 将postView转化为响应体
     *
     * @param records 记录列表
     * @param userInfos 用户信息
     * @param postListImgs 帖子图片
     * @param topicMap 话题
     * @param likeUserIdsMap 点赞用户
     * @param likeSet 判断当前请求用户是否点赞
     * @param userId 当前请求用户
     * @return List<PostListVO>
     */
    private List<PostListVO> convertToPostListVO(List<PostView> records,
                                                 Map<Integer, UserSimpleVO> userInfos,
                                                 Map<Integer,List<ImageAnnexView>> postListImgs,
                                                 Map<Integer, List<Topic>> topicMap,
                                                 Map<Integer, List<Integer>> likeUserIdsMap,
                                                 Map<Integer, CommentAnswerVO> commentMaps,
                                                 Set<String> likeSet, Integer userId){
        // 创建响应对象
        List<PostListVO> postData = new ArrayList<>();
        // todo 一次查询所有帖子的点赞用户信息，在循环中赋值
        for (PostView record : records) {
            PostListVO postListVO = new PostListVO();
            BeanUtils.copyProperties(record, postListVO);
            // 设置帖子用户信息
            if (PostIsAnonymousEnum.YES.getValue().equals(record.getIsAnonymous())){
                postListVO.setUserInfo(userFactory.buildAnonymityUserInfo());
            }else{
                postListVO.setUserInfo(userInfos.get(record.getUserId()));
            }
            // 设置该帖子图片信息
            postListVO.setImgList(postListImgs.get(record.getId()));
            // 设置点赞用户信息
            if (likeUserIdsMap.get(record.getId()) != null) {
                List<UserSimpleVO> userLikeInfos = userClient.getUserDeatilInfoList(likeUserIdsMap.get(record.getId())).getData();
                postListVO.setUserLikeList(userLikeInfos);
            }
            // 设置最热评论
            postListVO.setPostHotComment(commentMaps.get(record.getId()));
            // 设置帖子内容
            postListVO.setTopics(topicMap.get(postListVO.getId()));
            // html转码
            postListVO.setContent(HtmlUtils.htmlUnescape(record.getContent()));
            // 设置投票信息
            VoteVO voteVO;
            if (record.getUserId() != null) {
                voteVO = voteService.getVoteDetail(record.getId(), record.getUserId());
            } else {
                voteVO = voteService.getVoteDetail(record.getId(), null);
            }
            // 设置当前请求用户是否点赞
            if (likeSet.contains(getPostIsLikeKey(record.getId(), userId))){
                postListVO.setIsLike(true);
            }
            postListVO.setVoteMessage(voteVO);
            postData.add(postListVO);
        }
        return postData;
    }

    /**
     * 组合key
     * @param postId 帖子id
     * @param userId 用户id
     * @return 组合key
     */
    private String getPostIsLikeKey(Integer postId, Integer userId){
        return postId + "-" + userId;
    }

}
