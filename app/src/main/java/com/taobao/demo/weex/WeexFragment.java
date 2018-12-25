package com.taobao.demo.weex;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.emas.weex.EmasWeex;
import com.emas.weex.bundle.WeexPageFragment;
import com.taobao.weex.WXSDKInstance;

public class WeexFragment extends WeexPageFragment {

    private static final String TAG = "WeexFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnableDowngrade(true);//打开weex跨平台降级处理
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPause() {
        WeexCrashListener.mCrashUrl = "";
        super.onPause();

    }

    @Override
    public void onResume() {
        WeexCrashListener.mCrashUrl = getUrl();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
