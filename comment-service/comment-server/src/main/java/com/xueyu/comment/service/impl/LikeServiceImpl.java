package com.xueyu.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.comment.exception.CommentException;
import com.xueyu.comment.mapper.CommentMapper;
import com.xueyu.comment.mapper.LikeMapper;
import com.xueyu.comment.pojo.domain.Comment;
import com.xueyu.comment.pojo.domain.Like;
import com.xueyu.comment.pojo.vo.LikeVO;
import com.xueyu.comment.service.LikeService;
import com.xueyu.user.client.UserClient;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {

    @Resource
    LikeMapper likeMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    UserClient userClient;

    @Override
    public boolean isLike(Integer userId, Integer commentId) {
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getUserId,userId)
                    .eq(Like::getCommentId,commentId);
        Long count = likeMapper.selectCount(queryWrapper);
        return count>0;
    }

    @Override
    public boolean like(Integer userId, Integer commentId) {
        //数据库查询该点赞记录
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getCommentId,commentId)
                .eq(Like::getUserId,userId);
        Like like = likeMapper.selectOne(queryWrapper);
        //判断点赞数据是否为空，若为空则没有点赞记录，添加点赞数据
        if(like==null){
            like = new Like(null,userId,commentId,null);
            likeMapper.insert(like);
            commentMapper.updateLikeNum(commentId,1);
            log.info("用户 id -> {}, 点赞 评论 id -> {}", userId, commentId);
            return true;
        }
        //点赞数据不为空，说明已经点赞，则进行取消点赞
        likeMapper.delete(queryWrapper);
        commentMapper.updateLikeNum(commentId,-1);
        log.info("用户 id -> {}, 取消点赞 评论 id -> {}", userId, commentId);
        return false;
    }

    @Override
    public List<LikeVO> getUserLikeList(Integer userId) {
        if(userId==null) {
            throw new CommentException("用户状态异常");
        }
        //查出该用户点赞的所有评论
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getUserId,userId);
        queryWrapper.orderByDesc(Like::getCreateTime);
        List<Like> likeList = likeMapper.selectList(queryWrapper);
        if (likeList.size() == 0) {
            return new ArrayList<>();
        }
        //查出对应的评论信息
        List<Integer> commentIds = new ArrayList<>();
        for(Like like:likeList)
        {
            commentIds.add(like.getCommentId());
        }
        List<Comment> commentList = commentMapper.selectBatchIds(commentIds);

        //查询出被点赞评论的用户信息和评论关系的映射
        Map<Integer,Comment> commentInfo = new HashMap<>();
        Set<Integer> userIds = new HashSet<>();
        for(Comment comment:commentList)
        {
            userIds.add(comment.getUserId());
            commentInfo.put(comment.getId(),comment);
        }
        List<Integer> userList = new ArrayList<>(userIds);
        Map<Integer, UserSimpleVO> userInfo = userClient.getUserDeatilInfoMap(userList).getData();

        //stream流把comment对象赋值到likeVO中
        return likeList.stream().map((item) -> {
            LikeVO likeVO = new LikeVO();
            likeVO.setLikeId(item.getLikeId());
            likeVO.setUserInfo(userInfo.get(item.getUserId()));
            likeVO.setContent(commentInfo.get(item.getCommentId()).getContent());
            likeVO.setCreateTime(item.getCreateTime());
            return likeVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LikeVO> getUserBeLikedList(Integer userId) {
        if(userId==null) {
            throw new CommentException("用户状态异常");
        }
        //查询出该用户的所有评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId,userId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //统计这些评论的id
        List<Integer> commentIds = new ArrayList<>();
        //创建评论id和评论内容的映射
        Map<Integer,String> commentInfo = new HashMap<>();
        for(Comment comment:comments)
        {
            commentIds.add(comment.getId());
            commentInfo.put(comment.getId(),comment.getContent());
        }
        //查询出点赞关系
        LambdaQueryWrapper<Like> likeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        likeLambdaQueryWrapper.in(Like::getCommentId,commentIds);
        likeLambdaQueryWrapper.orderByDesc(Like::getCreateTime);
        List<Like> likes = likeMapper.selectList(likeLambdaQueryWrapper);

        //查出点赞用户信息
        Set<Integer> userIds = new HashSet<>();
        for(Like like:likes)
        {
            userIds.add(like.getUserId());
        }
        List<Integer> userList = new ArrayList<>(userIds);
        Map<Integer, UserSimpleVO> userInfo = userClient.getUserDeatilInfoMap(userList).getData();
        //stream流把comment对象赋值到likeVO中
        return likes.stream().map((item) -> {
            LikeVO likeVO = new LikeVO();
            likeVO.setLikeId(item.getLikeId());
            likeVO.setContent(commentInfo.get(item.getCommentId()));
            likeVO.setUserInfo(userInfo.get(item.getUserId()));
            likeVO.setCreateTime(item.getCreateTime());
            return likeVO;
        }).collect(Collectors.toList());
    }


}
