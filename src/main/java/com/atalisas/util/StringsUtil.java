package com.atalisas.util;

/**
 * Created by 顾文涛 on 2017/12/12.
 */
public class StringsUtil {
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
