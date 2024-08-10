package com.xueyu.comment.client;

import com.xueyu.comment.request.PostCommentQueryRequest;
import com.xueyu.comment.sdk.vo.CommentAnswerVO;
import com.xueyu.comment.sdk.vo.CommentPostVO;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.common.core.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.xueyu.common.core.enums.ResultTypeEnum.SERVICE_EXCEPTION;

/**
 * @author durance
 */
@Slf4j
@Component
public class CommentClientResolver implements CommentClient {

	@Override
	public RestResult<ListVO<CommentPostVO>> getPostCommentList(PostCommentQueryRequest request) {
		log.error("comment 服务异常：getPostCommentList 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

	@Override
	public RestResult<List<CommentAnswerVO>> postsMaxHotComment(List<Integer> postIds) {
		log.error("comment 服务异常：postsMaxHotComment 请求失败");
		return new RestResult<>(SERVICE_EXCEPTION.getCode(), SERVICE_EXCEPTION.getDesc());
	}

}
