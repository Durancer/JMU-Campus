package com.xueyu.comment.client;

import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author durance
 */
@Slf4j
@Component
public class CommentClientResolver implements CommentClient {

	@Override
	public RestResult<Object> getPostCommentList(Integer userId, Integer postId) {
		log.error("comment 服务异常：getPostCommentList 请求失败");
		return new RestResult<>(503, "fail");
	}

	@Override
	public RestResult<List<CommentAnswerVO>> postsMaxHotComment(List<Integer> postIds) {
		log.error("comment 服务异常：getPostCommentList 请求失败");
		return new RestResult<>(503, "fail");
	}

}
