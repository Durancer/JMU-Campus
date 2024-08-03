package com.xueyu.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.PostQueryRequest;
import com.xueyu.post.pojo.domain.Post;
import com.xueyu.post.pojo.vo.PostView;

/**
 * 帖子操作业务层
 *
 * @author durance
 */
public interface PostViewService extends IService<PostView> {

	/**
	 * 管理站帖子查询
	 * @param request req
	 * @return
	 */
	ListVO<PostView> getManagePostListPage(PostQueryRequest request);

}
