package com.taobao.demo.weex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.luyujie.innovationcourse.R;
import com.taobao.demo.Utils;

public class WeexActivity extends BaseWeexActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weex_activity_main);
        setCustomActionBar();
        if (getIntent().getData() != null) {
            // navigator模块跳转进入
            setActionBarTitle(R.string.weex_detail);
        } else {
            // 首页
            setActionBarTitle(R.string.weex_home2);
            showScan();
            hideBack();
        }

        //disbable bottom
        View bottomNavBar = findViewById(R.id.bottom_Navigation);
        bottomNavBar.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(mBundleUrl)) {
            createFragment(mBundleUrl);
        } else {
            JSONObject jobj = Utils.getWeexContainerJobj(mContext);
            JSONArray jsSources = JSON.parseArray(jobj.getString("JsSource"));
            String jsSrc =  "index.js";
            if (jsSources != null && jsSources.size() > 0) {
                jsSrc = (String)jsSources.get(0);
            }
            createFragment(jsSrc);
        }
    }
}
