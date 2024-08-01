package com.xueyu.comment.client;

import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.common.core.result.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author durance
 */
@FeignClient(value = "comment-server", fallback = CommentClientResolver.class)
public interface CommentClient {


	/**
	 * 得到帖子评论列表
	 *
	 * @param userId 用户id
	 * @param postId post id
	 * @return {@link RestResult}<{@link Object}>
	 */
	@GetMapping("comment/post/list")
	RestResult<Object> getPostCommentList(@RequestParam Integer userId, @RequestParam Integer postId);

	/**
	 * 获取各个帖子热度最高的评论
	 *
	 * @param postIds 帖子id 集合
	 * @return answerVO对象
	 */
	@GetMapping("comment/post/hot")
	RestResult<List<CommentAnswerVO>> postsMaxHotComment(@RequestParam List<Integer> postIds);

}
