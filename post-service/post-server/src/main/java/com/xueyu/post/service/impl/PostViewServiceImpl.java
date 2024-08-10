package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.common.core.result.ListVO;
import com.xueyu.post.request.PostQueryRequest;
import com.xueyu.post.mapper.PostViewMapper;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.service.PostViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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
        if (StringUtils.isNotEmpty(request.getTitle())){
            wrapper.like(PostView::getTitle, request.getTitle());
        }
        if (StringUtils.isNotEmpty(request.getContent())){
            wrapper.like(PostView::getContent, request.getContent());
        }
        wrapper.orderByDesc(PostView::getCreateTime);
        IPage<PostView> page = new Page<>(request.getCurrent(), request.getSize());
        query().getBaseMapper().selectPage(page, wrapper);
        for (PostView record : page.getRecords()) {
            record.setContent(HtmlUtils.htmlUnescape(record.getContent()));
        }
        ListVO<PostView> result = new ListVO<>();
        BeanUtils.copyProperties(page, result);
        return result;
    }

}
