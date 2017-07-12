package com.thinkgem.jeesite.modules.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by boxiaotong on 2017/2/13.
 */
public class Base64Util {
    public static void main(String args[]) throws IOException {
        String content = "123";
        String secret=encode(content);
        System.out.println(secret);
        String rs=decode(secret);
        System.out.println(rs);
    }

    //加密
    public static String encode(String str) throws UnsupportedEncodingException {
        String s = null;
        byte[] b = str.getBytes("utf-8");
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static String decode(String s) throws IOException {
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(s);
            result = new String(b, "utf-8");
        }
        return result;
    }

}

