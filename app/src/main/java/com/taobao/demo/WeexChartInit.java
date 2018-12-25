package com.taobao.demo;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import org.weex.plugin.WeexAceChart.WXAChartComponent;
import org.weex.plugin.WeexAceChart.WXAcePieChartComponent;
import org.weex.plugin.WeexAceChart.WXAceRoseChartComponent;
import org.weex.plugin.WeexAceChart.WXScatterPlotChartComponent;

public class WeexChartInit {
    public static void init() {
        //WEEX图表商业组件初始化
        try {
            WXSDKEngine.registerComponent("acechartandroid", WXAChartComponent.class);
            WXSDKEngine.registerComponent("acechartandroidrose", WXAceRoseChartComponent.class);
            WXSDKEngine.registerComponent("acechartandroidpie", WXAcePieChartComponent.class);
            WXSDKEngine.registerComponent("acechartandroidstack", WXScatterPlotChartComponent.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
