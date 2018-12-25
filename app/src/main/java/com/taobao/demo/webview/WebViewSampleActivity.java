package com.taobao.demo.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.taobao.windvane.fragment.WVWebViewFragment;
import android.taobao.windvane.webview.WVWebViewClient;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.example.luyujie.innovationcourse.R;
import com.emas.hybrid.view.EmasHybridWebViewHelper;
import com.taobao.demo.BaseActivity;


/**
 * created by chenluan.cht@alibaba-inc.com
 * at 2018/10/16
 */
public class WebViewSampleActivity extends BaseActivity {
    private Fragment webViewFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_sample);

        setCustomActionBar();
        setActionBarTitle(R.string.app_name);

        webViewFragment = EmasHybridWebViewHelper.getInstance().installWebView(
                this,
                getIntent().getData(),
                R.id.root,
                new WVWebViewClient(this) {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        Log.d("test", view.getTitle());
                    }
                }, null);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewFragment != null) {
            boolean result = ((WVWebViewFragment) webViewFragment).onBackPressed();
            return result || super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        webViewFragment.onActivityResult(requestCode, resultCode, data);
    }

}
