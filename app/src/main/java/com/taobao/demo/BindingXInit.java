package com.taobao.demo;

import com.alibaba.android.bindingx.plugin.weex.BindingX;
import com.taobao.weex.common.WXException;

public class BindingXInit {
    public static void init() {
        //扩展UI库weex-ui初始化
        try {
            BindingX.register();
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
