package com.xueyu.comment.controller;

import com.xueyu.comment.pojo.vo.LikeVO;
import com.xueyu.comment.service.LikeService;
import com.xueyu.common.core.result.RestResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("comment/like")
public class LikeController {

    @Resource
    LikeService likeService;

    /**
     * 点赞或取消点赞
     *
     * @param userId    用户id
     * @param commentId 评论id
     * @return {@link RestResult}<{@link ?}>
     */
    @PostMapping
    public RestResult<?> like(@RequestHeader Integer userId, @RequestParam Integer commentId) {
        return RestResult.ok(likeService.like(userId, commentId));
    }

    /**
     * 用户点赞的所有评论
     *
     * @param userId 用户id
     * @return {@link RestResult}<{@link List}<{@link LikeVO}>>
     */
    @GetMapping("/common")
    public RestResult<List<LikeVO>> getUserLikeList(@RequestHeader Integer userId){
        return RestResult.ok(likeService.getUserLikeList(userId));
    }

    /**
     * 查询用户被点赞的所有评论
     *
     * @param userId 用户id
     * @return {@link RestResult}<{@link List}<{@link LikeVO}>>
     */
    @GetMapping("/user/common")
    public RestResult<List<LikeVO>> getUserBeLikedList(@RequestHeader Integer userId){
        return RestResult.ok(likeService.getUserBeLikedList(userId));
    }

}
