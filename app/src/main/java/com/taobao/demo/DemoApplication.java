package com.taobao.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by guanjie on 15/8/20.
 */
public class DemoApplication extends Application {

    private Context mContext;
    private static final String TAG = "APP";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("APP", "DemoApplication on create");
        Utils.initConfig(this);
        mContext = this;
        //支持读取本地配置
        EmasInit emas = EmasInit.getInstance();
        //初始化通道服务
        emas.initAccs();
        // 初始化配置中心
        emas.initConfig(this);
        //初始化高可用
        emas.initHA();
        //初始化应用更新
        emas.initUpdate();
        //初始化Weex
        emas.initWeex();
        // 初始化数据分析
        emas.initMAN(this);
        // 初始化H5容器
        HybridInit.init(this);

        //Weex可选库初始化
        BindingXInit.init();
        WeexChartInit.init();

    }
}