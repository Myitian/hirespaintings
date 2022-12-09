package com.myitian;

public class Util {
    public static boolean isNullOrBlank(String param) {
        return param == null || param.trim().length() == 0;
    }
}
