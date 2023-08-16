package com.xueyu.user.pojo.vo;

import com.xueyu.user.pojo.domain.UserGeneral;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户统计响应 vo
 *
 * @author durance
 */
@Data
@NoArgsConstructor
public class UserGeneralVO {

    UserView userView;

    UserGeneral userGeneral;

}
