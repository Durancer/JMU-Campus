package com.xueyu.comment.pojo.vo;

import com.xueyu.user.sdk.pojo.vo.UserSimpleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeVO {

    /**
     * 点赞表主键
     */
    Integer likeId;

    /**
     * 用户信息
     */
    UserSimpleVO userInfo;

    /**
     * 评论内容
     */
    String content;

    /**
     * 点赞创建时间
     */
    Date createTime;

}
