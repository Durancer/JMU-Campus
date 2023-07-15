package com.xueyu.post.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xueyu.post.mapper.PostViewMapper;
import com.xueyu.post.pojo.vo.HotPostVO;
import com.xueyu.post.pojo.vo.PostView;
import com.xueyu.post.service.HotPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.xueyu.post.sdk.constant.PostMqContants.*;


@Service
@Transactional
public class HotPostServiceImpl implements HotPostService {

    @Resource
    PostViewMapper postViewMapper;

    @Resource
    RedisTemplate<String,String> redisTemplate;

    @Override
    public void searchHotPost() {

        //1.查询前一周的帖子数据
        LambdaQueryWrapper<PostView> queryWrapper = new LambdaQueryWrapper<>();
        //计算时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.WEEK_OF_MONTH,-1);
        Timestamp time = new Timestamp(calendar.getTimeInMillis());
        queryWrapper.lt(PostView::getCreateTime,now);
        queryWrapper.gt(PostView::getCreateTime,time);
        List<PostView> postViewlist = postViewMapper.selectList(queryWrapper);

        //2.计算帖子的分数
        List<HotPostVO> hotPostVOList = computeHotPost(postViewlist);

        //3.前20条数据存入redis
        cacheHotToRedis(hotPostVOList);
    }

    /**
     * 缓存热门帖子
     *
     * @param hotPostVOList 热门帖子列表
     */
    private void cacheHotToRedis(List<HotPostVO> hotPostVOList) {

        hotPostVOList = hotPostVOList.stream().sorted(Comparator.comparing(HotPostVO::getScore).reversed()).collect(Collectors.toList());
        if (hotPostVOList.size() > 20) {
            hotPostVOList = hotPostVOList.subList(0, 20);
        }
        redisTemplate.opsForValue().set(HOT_POST_KEY,JSON.toJSONString(hotPostVOList));
    }

    /**
     * 计算帖子分数
     *
     * @param postViewlist 帖子列表
     * @return {@link List}<{@link HotPostVO}>
     */
    private List<HotPostVO> computeHotPost(List<PostView> postViewlist) {
        List<HotPostVO> list = new ArrayList<>();
        if(postViewlist != null && postViewlist.size() > 0){
            for(PostView postView : postViewlist){
                HotPostVO hotPostVO = new HotPostVO();
                BeanUtils.copyProperties(postView,hotPostVO);
                hotPostVO.setScore(computeScore(postView));
                list.add(hotPostVO);
            }
        }
        return list;
    }

    /**
     * 计算分数
     *
     * @param postView 帖子
     * @return {@link Integer}
     */
    private Integer computeScore(PostView postView) {
        Integer score = 0;
        if(postView.getLikeNum() != null){
            score += postView.getLikeNum() * HOT_POST_LIKE_WEIGHT;
        }
        if(postView.getViewNum() != null){
            score += postView.getViewNum() * HOT_POST_VIEW_WEIGHT;
        }
        if(postView.getCommentNum() != null){
            score += postView.getCommentNum() * HOT_POST_COMMENT_WEIGHT;
        }
        return score;
    }

}
