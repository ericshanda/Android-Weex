package com.taobao.demo.orange.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.luyujie.innovationcourse.R;
import com.taobao.orange.OConfigListener;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.util.OLog;

import java.util.Map;


public class FeatureActivity extends AppCompatActivity {
    private static final String TAG = "FeatureActivity";

    public static final String NAMESPACE_FEATURE = "feature";
    public static final String KEY_FEATURE_ENABLE = "feature_enable";
    public static final String KEY_FEATURE_URL = "feature_url";
    public static final String DEFAULT_FEATURE_URL = "http://www.baidu.com";

    TextView featureUrlText;
    WebView featureWebView;

    private OConfigListener mListener = new OConfigListener() {
        @Override
        public void onConfigUpdate(String namespace, Map<String, String> args) {
            OLog.d(TAG, "onConfigUpdate", "namespace", namespace, "args", args);
            if (NAMESPACE_FEATURE.equals(namespace)) {
                updateUrl();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        featureWebView.getSettings().setJavaScriptEnabled(true);
        featureWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        featureUrlText = findViewById(R.id.feature_url);
        featureWebView = findViewById(R.id.feature_web);
        updateUrl();
        OrangeConfig.getInstance().registerListener(new String[]{NAMESPACE_FEATURE}, mListener);
    }

    private void updateUrl() {
        final String url = OrangeConfig.getInstance().getConfig(NAMESPACE_FEATURE, KEY_FEATURE_URL, DEFAULT_FEATURE_URL);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                featureUrlText.setText(String.format("%s = %s", KEY_FEATURE_URL, url));
                if (!TextUtils.isEmpty(url)) {
                    featureWebView.loadUrl(url);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OrangeConfig.getInstance().unregisterListener(new String[]{NAMESPACE_FEATURE}, mListener);
    }
}
