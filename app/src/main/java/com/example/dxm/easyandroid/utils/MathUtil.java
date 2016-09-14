package com.example.dxm.easyandroid.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duxiaoming on 16/8/8.
 */
public class MathUtil {

    /**
     * 判断是不是手机号
     *
     * @param mobiles 手机号
     * @return
     */
    public static boolean isMobileNo(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是不是邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);


        return m.matches();
    }
}
