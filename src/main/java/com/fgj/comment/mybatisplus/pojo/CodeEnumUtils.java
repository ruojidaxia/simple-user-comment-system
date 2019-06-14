package com.fgj.comment.mybatisplus.pojo;

import com.fgj.comment.mybatisplus.enums.CodeEnum;

public class CodeEnumUtils {
    public static <T extends Enum<?> & CodeEnum> T valueOf(int code, Class<T> type){
        T[] enums = type.getEnumConstants();
        for(T t : enums){
            if(t.getCode() == code){
                return t;
            }
        }
        return null;
    }
}
