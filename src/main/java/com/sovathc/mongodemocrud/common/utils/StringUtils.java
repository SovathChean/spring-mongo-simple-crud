package com.sovathc.mongodemocrud.common.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public static String removeStringWithLength(String text, int length) {
        if (text.length() <= length) {
            return text;
        } else {
            return text.substring(0, length);
        }
    }
}
