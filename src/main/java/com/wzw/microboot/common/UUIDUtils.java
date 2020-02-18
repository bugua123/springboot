package com.wzw.microboot.common;

import java.util.UUID;

public class UUIDUtils {
    /**
     * UUID原格式
     * @return
     */
    public static String getId(){
              return UUID.randomUUID().toString();
         }

    /**
     * UUID去掉空格
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
