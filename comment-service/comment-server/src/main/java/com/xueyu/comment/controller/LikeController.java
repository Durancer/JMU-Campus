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

    @PostMapping
    public RestResult<?> like(@RequestHeader Integer userId, @RequestParam Integer commentId) {
        return RestResult.ok(likeService.like(userId, commentId));
    }

    @GetMapping("/exist")
    public RestResult<Boolean> isLike(@RequestHeader Integer userId,@RequestParam Integer commentId) {
        return RestResult.ok(likeService.isLike(userId,commentId));
    }

    @GetMapping("/common")
    public RestResult<List<LikeVO>> likeCommons(@RequestHeader Integer userId){
        return RestResult.ok(likeService.likeCommons(userId));
    }

    @GetMapping("/user/common")
    public RestResult<List<LikeVO>> likeByCommons(@RequestHeader Integer userId){
        return RestResult.ok(likeService.likeByCommons(userId));
    }

    @PostMapping("/test")
    public RestResult test()
    {
        likeService.transLikedFromRedis2DB();
        return RestResult.ok();
    }

}
