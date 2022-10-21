package com.franzliszt.newbookkeeping.utils;

import android.util.Log;

public class RegexUtils {
    /**
     * 根据工信部2019年最新公布的手机号段
     * 等级：严谨*/
    public static boolean IsPhoneNumberMax(String str){
        String s = "^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$";
        return str.matches(s);
    }
    /**
     * 只要是13,14,15,16,17,18,19开头即可
     * 等级：较为宽松*/
    public static boolean IsPhoneNumberMin(String str){
        String s = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";
        return str.matches(s);
    }
    /**
     * 只要是1开头即可
     * 等级：最宽松*/
    public static boolean IsPhoneNumberLow(String str){
        String s = "^(?:(?:\\+|00)86)?1\\d{10}$";
        return str.matches(s);
    }
    /**
     * 是否为网站
     * 例如：www.baidu.com*/
    public static boolean IsURL(String str){
        String s = "^(((ht|f)tps?):\\/\\/)?([^!@#$%^&*?.\\s-]([^!@#$%^&*?.\\s]{0,63}[^!@#$%^&*?.\\s])?\\.)+[a-z]{2,6}\\/?";
        return str.matches(s);
    }

    /**
     * 是否为邮箱*/
    public static boolean IsEmail(String str){
        String s = "^(([^<>()[\\]\\\\.,;:\\s@\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        return str.matches(s);
    }

    /**
     * 是否为身份证
     * 二代：18位*/
    public static boolean IsID(String str){
        String s = "^[1-9]\\d{5}(?:18|19|20)\\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[\\dXx]$";
        return str.matches(s);
    }

    /**
     * 是否为汉字或中文*/
    public static boolean IsCNChat(String str){
        String s = "^(?:[\\u3400-\\u4DB5\\u4E00-\\u9FEA\\uFA0E\\uFA0F\\uFA11\\uFA13\\uFA14\\uFA1F\\uFA21\\uFA23\\uFA24\\uFA27-\\uFA29]|[\\uD840-\\uD868\\uD86A-\\uD86C\\uD86F-\\uD872\\uD874-\\uD879][\\uDC00-\\uDFFF]|\\uD869[\\uDC00-\\uDED6\\uDF00-\\uDFFF]|\\uD86D[\\uDC00-\\uDF34\\uDF40-\\uDFFF]|\\uD86E[\\uDC00-\\uDC1D\\uDC20-\\uDFFF]|\\uD873[\\uDC00-\\uDEA1\\uDEB0-\\uDFFF]|\\uD87A[\\uDC00-\\uDFE0])+$";
        return str.matches(s);
    }

    /**
     * 是否为小数*/
    public static boolean IsDecimal(String str){
        String s = "^\\d+\\.\\d+$";
        return str.matches(s);
    }

    /**
     * 是否为数字*/
    public static boolean IsNumber(String str){
        String s = "^\\d{1,}$";
        return str.matches(s);
    }

    /**
     * 是否为英文字母*/
    public static boolean IsENChat(String str){
        String s = "^\\d{1,}$";
        return str.matches(s);
    }

    /**
     * 是否为大写英文字母*/
    public static boolean IsHighCase(String str){
        String s = "^[A-Z]+$";
        return str.matches(s);
    }

    /**
     * 是否为小写英文字母*/
    public static boolean IsLowCase(String str){
        String s = "^[a-z]+$";
        return str.matches(s);
    }

    /**
     * 密码强度校验，最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符*/
    public static boolean PassWordStrength(String str){
        String s = "^\\S*(?=\\S{6,})(?=\\S*\\d)(?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*[!@#$%^&*? ])\\S*$";
        return str.matches(s);
    }

}
