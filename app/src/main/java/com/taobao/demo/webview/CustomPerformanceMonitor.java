package com.taobao.demo.webview;

import android.taobao.windvane.monitor.WVMonitorImpl;
import android.util.Log;

/**
 * 自定义H5容器监控示例
 * created by chenluan.cht@alibaba-inc.com
 * at 2018/10/8
 */
public class CustomPerformanceMonitor extends WVMonitorImpl {

    private static long start = 0;

    @Override
    public void didPageStartLoadAtTime(String url, long time) {
        super.didPageStartLoadAtTime(url, time);
        start = time;

    }

    @Override
    public void didPageFinishLoadAtTime(String url, long time) {
        super.didPageFinishLoadAtTime(url, time);
        long elapse = time - start;
        Log.d("test", ">>> " + elapse);
    }
}
