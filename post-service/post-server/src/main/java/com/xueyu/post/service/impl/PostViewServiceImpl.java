package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.PostQueryRequest;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.PostViewMapper;
import com.xueyu.post.mapper.VoteMapper;
import com.xueyu.post.mapper.VoteOptionMapper;
import com.xueyu.post.mapper.VoteRecordMapper;
import com.xueyu.post.pojo.domain.Vote;
import com.xueyu.post.pojo.domain.VoteOption;
import com.xueyu.post.pojo.domain.VoteRecord;
import com.xueyu.post.pojo.enums.VoteCycle;
import com.xueyu.post.pojo.enums.VoteType;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.pojo.vo.VoteOptionVO;
import com.xueyu.post.pojo.vo.VoteVO;
import com.xueyu.post.service.PostViewService;
import com.xueyu.post.service.VoteRecordService;
import com.xueyu.post.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author fj
 */
@Service
@Slf4j
public class PostViewServiceImpl extends ServiceImpl<PostViewMapper, PostView> implements PostViewService {

    @Override
    public ListVO<PostView> getManagePostListPage(PostQueryRequest request) {
        LambdaQueryWrapper<PostView> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(request.getStatus())){
            wrapper.eq(PostView::getStatus, request.getStatus());
        }
        if (Objects.nonNull(request.getIsPrivate())){
            wrapper.eq(PostView::getIsPrivate, request.getIsPrivate());
        }
        if (Objects.nonNull(request.getUserId())){
            wrapper.eq(PostView::getUserId, request.getUserId());
        }
        if (Objects.nonNull(request.getCreateTime())){
            wrapper.ge(PostView::getCreateTime, request.getCreateTime());
        }
        if (Objects.nonNull(request.getTitle())){
            wrapper.like(PostView::getTitle, request.getTitle());
        }
        if (Objects.nonNull(request.getContent())){
            wrapper.like(PostView::getContent, request.getContent());
        }
        IPage<PostView> page = new Page<>(request.getCurrent(), request.getSize());
        query().getBaseMapper().selectPage(page, wrapper);
        ListVO<PostView> result = new ListVO<>();
        BeanUtils.copyProperties(page, result);
        return result;
    }

}
