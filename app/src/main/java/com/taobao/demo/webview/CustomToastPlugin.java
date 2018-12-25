package com.taobao.demo.webview;

import android.taobao.windvane.jsbridge.WVApiPlugin;
import android.taobao.windvane.jsbridge.WVCallBackContext;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 自定义JS Bridge API示例
 *
 * created by chenluan.cht@alibaba-inc.com
 * at 2018/10/25
 */
public class CustomToastPlugin extends WVApiPlugin {
    @Override
    protected boolean execute(String action, String params, WVCallBackContext wvCallBackContext) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(params);
            String content = jsonObject.optString("content");
            if ("long".equals(action)) {
                Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
            } else if ("short".equals(action)) {
                Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }
}
