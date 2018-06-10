package com.admin.common.utils;

import java.util.UUID;

/**
 * 描述
 * 
 * @author hepeng
 * @date 2018/3/24 9:54
 */
public class UUidUtil {

    public static String getUUid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
