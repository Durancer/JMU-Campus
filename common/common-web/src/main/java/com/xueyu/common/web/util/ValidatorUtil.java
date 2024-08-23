package com.xueyu.common.web.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xueyu.common.core.exception.BusinessException;

/**
 * 格式校验工具类
 *
 * @author durance
 */
public class ValidatorUtil {

    /**
     * 校验是否为邮箱
     *
     * @param str 待校验的字符串
     * @return 如果字符串全为字母，则返回true；否则返回false
     */
    public static boolean verifyEmail(String str){
        if (StringUtils.isEmpty(str)){
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return str.matches(emailRegex);
    }

    /**
     * 校验是否为纯字母字符串
     *
     * @param str 待校验的字符串
     * @return 如果字符串全为字母，则返回true；否则返回false
     */
    public static boolean verifyPureLetter(String str) {
        if (StringUtils.isEmpty(str)){
            return false;
        }
        String letterRegex = "^[a-zA-Z]+$";
        return str.matches(letterRegex);
    }

    /**
     * 校验字符串是否只包含数字
     *
     * @param str 待校验的字符串
     * @return 如果字符串只包含数字，则返回true；否则返回false
     */
    public static boolean verifyPureNumber(String str) {
        if (StringUtils.isEmpty(str)){
            return false;
        }
        String numberRegex = "^\\d+$";
        return str.matches(numberRegex);
    }

    /**
     * 校验字符串是否只包含数字和字母（不区分大小写）
     *
     * @param str 待校验的字符串
     * @return 如果字符串只包含数字和字母，则返回true；否则返回false
     */
    public static boolean verifyNumberAndLetter(String str) {
        String alphanumericRegex = "^[a-zA-Z0-9]+$";
        return str.matches(alphanumericRegex);
    }

}
