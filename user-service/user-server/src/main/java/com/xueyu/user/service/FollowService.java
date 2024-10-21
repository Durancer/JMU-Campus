package com.xueyu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.pojo.domain.UserFollow;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.request.FollowGetRequest;
import com.xueyu.user.request.FollowRequest;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;

import java.util.List;

/**
 * 活动相关业务
 *
 * @author durance
 */
public interface FollowService extends IService<UserFollow> {

    /**
     * 关注|取消 请求
     * @param request req
     * @return
     */
    boolean followUser(FollowRequest request);

    /**
     * 获取关注列表
     * @param request req
     * @return
     */
    ListVO<UserSimpleVO> getFollowList(FollowGetRequest request);

    /**
     * 获取粉丝列表
     * @param request req
     * @return
     */
    ListVO<UserSimpleVO> getFansList(FollowGetRequest request);


}
