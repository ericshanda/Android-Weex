package com.taobao.demo.orange;

import com.taobao.orange.OConstant;

/**
 * Created by wuer on 2018/4/19.
 */

public class GlobalDemo {
    public static final String SP_CONFIG = "orange_config";
    public static final String SP_CANDIDATE = "orange_candidate";

    public static final String SP_KEY_APPKEY = "APPKEY";
    public static final String SP_KEY_APPSECRET = "APPSECRET";

    public static OConstant.ENV env = OConstant.ENV.PREPARE;

    public static String appVersion;
    public static String appKey;
    public static String appSecret;
    public static String host;
}
