package com.xueyu.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyu.post.exception.PostException;
import com.xueyu.post.mapper.VoteMapper;
import com.xueyu.post.mapper.VoteOptionMapper;
import com.xueyu.post.mapper.VoteRecordMapper;
import com.xueyu.post.pojo.domain.*;
import com.xueyu.post.pojo.enums.VoteCycle;
import com.xueyu.post.pojo.enums.VoteType;
import com.xueyu.post.pojo.vo.VoteOptionVO;
import com.xueyu.post.pojo.vo.VoteVO;
import com.xueyu.post.service.VoteRecordService;
import com.xueyu.post.service.VoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {

    @Resource
    VoteMapper voteMapper;

    @Resource
    VoteRecordService voteRecordService;

    @Resource
    VoteOptionMapper voteOptionMapper;

    @Resource
    VoteRecordMapper voteRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean launchVote(Vote vote, String[] options) {
        //判断投票类型
        if (!(vote.getType().equals(VoteType.MULTIPLE.getValue()) || vote.getType().equals(VoteType.RADIO.getValue()))) {
            throw new PostException("错误的投票类型");
        }
        //判断投票周期
        if (!(vote.getCycle().equals(VoteCycle.DAY.getValue()) || vote.getCycle().equals(VoteCycle.WEEK.getValue()) || vote.getCycle().equals(VoteCycle.MONTH.getValue()) || vote.getCycle().equals(VoteCycle.YEAR.getValue()))) {
            throw new PostException("错误的周期类型");
        }
        //投票数据存入数据库
        query().getBaseMapper().insert(vote);
        //判断投票选项是否存在
        if(options==null){
            throw new PostException("无投票选项");
        }
        //创建投票选项实例
        VoteOption voteOption;
        //添加投票选项
        for(String option : options){
            voteOption = new VoteOption(null,vote.getVoteId(),option,null);
            voteOptionMapper.insert(voteOption);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteVote(Integer voteId) {
        //查询投票数据是否存在
        Vote vote = voteMapper.selectById(voteId);
        if(vote==null){
            throw new PostException("该投票不存在");
        }
        //删除投票选项
        LambdaQueryWrapper<VoteOption> optionQueryWrapper = new LambdaQueryWrapper<>();
        optionQueryWrapper.eq(VoteOption::getVoteId,voteId);
        voteOptionMapper.delete(optionQueryWrapper);
        //删除投票记录
        LambdaQueryWrapper<VoteRecord> recordQueryWrapper = new LambdaQueryWrapper<>();
        recordQueryWrapper.in(VoteRecord::getVoteId,voteId);
        voteRecordMapper.delete(recordQueryWrapper);
        //删除投票数据
        int row = voteMapper.deleteById(voteId);
        if(row != 1){
            throw new PostException("投票删除异常");
        }
        return true;
    }

    @Override
    public VoteVO getVoteDetail(Integer postId, Integer userId) {
        //查询是否存在投票
        LambdaQueryWrapper<Vote> voteQueryWrapper = new LambdaQueryWrapper<>();
        voteQueryWrapper.eq(Vote::getPostId,postId);
        Vote vote = voteMapper.selectOne(voteQueryWrapper);
        if(vote!=null){
            VoteVO voteVO = new VoteVO();
            BeanUtils.copyProperties(vote,voteVO);
            //设置投票选项
            LambdaQueryWrapper<VoteOption> optionQueryWrapper = new LambdaQueryWrapper<>();
            optionQueryWrapper.eq(VoteOption::getVoteId,vote.getVoteId());
            List<VoteOption> optionList = voteOptionMapper.selectList(optionQueryWrapper);
            List<VoteOptionVO> optionVOList = new ArrayList<>();
            for(VoteOption voteOption : optionList){
                VoteOptionVO voteOptionVO = new VoteOptionVO();
                BeanUtils.copyProperties(voteOption,voteOptionVO);
                if(voteOption.getNum()!=0&vote.getAmount()!=0){
                    voteOptionVO.setRatio(String.format("%.1f",(double) voteOption.getNum()/vote.getAmount()*100));
                }else {
                    voteOptionVO.setRatio("0");
                }
                optionVOList.add(voteOptionVO);
            }
            voteVO.setOptionList(optionVOList);
            //设置是否过期
            voteVO.setExpired(voteRecordService.isVoteExpired(vote));
            //设置是否投票
            voteVO.setIsVote(voteRecordService.isVote(userId,vote.getVoteId()));
            return voteVO;
        }
        //不存在返回null
        return null;
    }

}
