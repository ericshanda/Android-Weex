package com.taobao.demo;

import android.app.Application;

import com.alibaba.ha.adapter.Sampling;
import com.example.luyujie.innovationcourse.BuildConfig;
import com.emas.hybrid.EmasHybrid;
import com.emas.hybrid.api.EmasHybridApi;
import com.emas.hybrid.monitor.EmasHybridMonitor;
import com.taobao.demo.webview.CustomPerformanceMonitor;
import com.taobao.demo.webview.CustomToastPlugin;

/**
 * created by chenluan.cht@alibaba-inc.com
 * at 2018/11/5
 */
public class HybridInit {
    public static void init(Application application) {
        EmasHybrid.getInstance()
                .setOpenLog(true)
                .setAppKey(EmasInit.getInstance().getAppkey())
                .setAppSecret(EmasInit.getInstance().getAppSecret())
                .setTtid(EmasInit.getInstance().mChannelID)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setZcacheEnable(true)
                .setZcacheUrl(EmasInit.getInstance().mCacheURL)
                .setOpenMonitor(true)
                .setUseUc(true)
                .setMonitorSample(Sampling.All)
                .setEnableDynamic(true)
                .init(application);

        EmasHybridMonitor.registerPerformanceMonitor(new CustomPerformanceMonitor());
        EmasHybridApi.registerPlugin("Toast", CustomToastPlugin.class);
    }
}
