package com.xueyu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.user.mapper.FollowMapper;
import com.xueyu.user.mapper.UserGeneralMapper;
import com.xueyu.user.mapper.UserViewMapper;
import com.xueyu.user.pojo.domain.UserFollow;
import com.xueyu.user.pojo.enums.FollowMutualEnum;
import com.xueyu.user.pojo.vo.UserView;
import com.xueyu.user.request.FollowGetRequest;
import com.xueyu.user.request.FollowRequest;
import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import com.xueyu.user.service.FollowService;
import com.xueyu.user.service.UserViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author durance
 */
@Service
@Slf4j
public class FollowServiceImpl extends ServiceImpl<FollowMapper, UserFollow> implements FollowService {

	@Resource
	FollowMapper followMapper;

	@Resource
	UserViewService userViewService;

	@Resource
	UserGeneralMapper userGeneralMapper;

	@Override
	public boolean followUser(FollowRequest request) {
		LambdaQueryWrapper<UserFollow> followWrapper = new LambdaQueryWrapper<>();
		followWrapper.eq(UserFollow::getUserId, request.getUserId());
		followWrapper.eq(UserFollow::getFollowId, request.getFollowId());
		UserFollow userFollow = followMapper.selectOne(followWrapper);
		LambdaQueryWrapper<UserFollow> fansWrapper = new LambdaQueryWrapper<>();
		fansWrapper.eq(UserFollow::getUserId, request.getFollowId());
		fansWrapper.eq(UserFollow::getFollowId, request.getUserId());
		UserFollow fans = followMapper.selectOne(fansWrapper);

		// 不存在，执行关注操作
		if (Objects.isNull(userFollow)){
			// 原本未关注，执行关注操作,查看对方是否关注登录用户
			UserFollow insert = new UserFollow();
			if (Objects.nonNull(fans)){
				// 相互关注，插入并更新，原相互关注
				insert.setIsMutual(FollowMutualEnum.YES.getCode());
				fans.setIsMutual(FollowMutualEnum.YES.getCode());
				followMapper.updateById(fans);
			}
			insert.setUserId(request.getUserId());
			insert.setFollowId(request.getFollowId());
			insert.setCreateTime(new Date());
			followMapper.insert(insert);
			// 添加统计表数据
			userGeneralMapper.updateFansNumByUserId(request.getFollowId(), 1);
			log.info("用户id ->{} 关注 用户id -> {}", request.getUserId(), request.getFollowId());
		}else {
			// 存在，执行取消关注操作
			if (Objects.nonNull(fans)){
				fans.setIsMutual(FollowMutualEnum.NO.getCode());
				followMapper.updateById(fans);
			}
			followMapper.deleteById(userFollow.getId());
			userGeneralMapper.updateFansNumByUserId(request.getFollowId(), -1);
			log.info("用户id ->{} 取消关注 用户id -> {}", request.getUserId(), request.getFollowId());
		}
		return true;
	}

	@Override
	public ListVO<UserSimpleVO> getFollowList(FollowGetRequest request) {
		ListVO<UserSimpleVO> listVO = new ListVO<>();
		LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserFollow::getUserId, request.getUserId());
		IPage<UserFollow> page = new Page<>(request.getCurrent(), request.getSize());
		followMapper.selectPage(page, wrapper);
		BeanUtils.copyProperties(page, listVO);
		List<UserFollow> records = page.getRecords();
		Map<Integer, UserFollow> followMap = records.stream().collect(Collectors.toMap(UserFollow::getFollowId, Function.identity()));
		List<Integer> followIds = records.stream().map(UserFollow::getFollowId).collect(Collectors.toList());
		List<UserSimpleVO> userInfoList = userViewService.getUserInfoList(followIds);
		userInfoList.forEach(o ->{
			try{
				o.setIsMutual(followMap.get(o.getId()).getIsMutual());
			}catch (NullPointerException e){
				log.error("获取相互关注信息失败", e);
			}
		});
		listVO.setRecords(userInfoList);
		return listVO;
	}

	@Override
	public ListVO<UserSimpleVO> getFansList(FollowGetRequest request) {
		ListVO<UserSimpleVO> listVO = new ListVO<>();
		LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserFollow::getFollowId, request.getUserId());
		IPage<UserFollow> page = new Page<>(request.getCurrent(), request.getSize());
		followMapper.selectPage(page, wrapper);
		BeanUtils.copyProperties(page, listVO);
		List<UserFollow> records = page.getRecords();
		Map<Integer, UserFollow> followMap = records.stream().collect(Collectors.toMap(UserFollow::getUserId, Function.identity()));
		List<Integer> followIds = records.stream().map(UserFollow::getUserId).collect(Collectors.toList());
		List<UserSimpleVO> userInfoList = userViewService.getUserInfoList(followIds);
		userInfoList.forEach(o ->{
			try{
				o.setIsMutual(followMap.get(o.getId()).getIsMutual());
			}catch (NullPointerException e){
				log.error("获取相互关注信息失败", e);
			}
		});
		listVO.setRecords(userInfoList);
		return listVO;
	}

}
