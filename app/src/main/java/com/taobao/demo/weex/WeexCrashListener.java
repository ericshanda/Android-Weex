package com.taobao.demo.weex;

import com.alibaba.ha.adapter.service.crash.JavaCrashListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyazhou on 2018/1/4.
 */

public class WeexCrashListener implements JavaCrashListener {
    public static String mCrashUrl = "";
    @Override
    public Map<String, Object> onCrashCaught(Thread thread, Throwable throwable) {
        Map<String, Object> map = new HashMap<>();
        map.put("wx_current_url", mCrashUrl);
        return map;
    }
}
