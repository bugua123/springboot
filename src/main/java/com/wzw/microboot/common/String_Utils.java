package com.wzw.microboot.common;

public class String_Utils {
    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    public  static String join(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }
}
