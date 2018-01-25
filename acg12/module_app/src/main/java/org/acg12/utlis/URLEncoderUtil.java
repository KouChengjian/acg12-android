package org.acg12.utlis;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/1/18.
 */

public class URLEncoderUtil {

    public static String encode(String key){
        String k = "";
        try {
            k  = URLEncoder.encode(key ,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            k = "";
        }
        return k;
    }
}
