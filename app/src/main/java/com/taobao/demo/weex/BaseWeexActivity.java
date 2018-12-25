package com.taobao.demo.weex;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.luyujie.innovationcourse.R;
import com.emas.weex.EmasWeex;
import com.emas.weex.bundle.WeexPageFragment;
import com.taobao.demo.BaseActivity;
import com.taobao.demo.SplashActivity;
import com.taobao.weex.utils.WXFileUtils;

import java.util.Set;

public class BaseWeexActivity extends BaseActivity {

    private static final String TAG = "BaseActivity";
    public static final String URL_BAR = "_wx_bar";
    protected String mBundleUrl;
    protected String mRenderUrl;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        requestPermission();
        Intent intent = getIntent();
        if (intent != null && !TextUtils.isEmpty(intent.getDataString())) {
            mBundleUrl = intent.getDataString();
        }
        mRenderUrl = getRenderUrl(mBundleUrl);
    }

    @Override
    protected void setCustomActionBar() {
        super.setCustomActionBar();
        if (!TextUtils.isEmpty(mBundleUrl) && "false".equals(Uri.parse(mBundleUrl).getQueryParameter(URL_BAR))) {
            getSupportActionBar().hide();
        }
    }

    protected WeexPageFragment createFragment(String jsSource){
        WeexPageFragment fragment;
        if (jsSource.startsWith("http")) {

            fragment = (WeexPageFragment) WeexPageFragment
                    .newInstanceWithUrl(this, WeexFragment.class, jsSource, getRenderUrl(jsSource), R.id.frame_root_layout);
        } else {
            fragment = (WeexPageFragment) WeexPageFragment.newInstanceWithTemplate(this, WeexFragment.class,
                    WXFileUtils.loadAsset(jsSource, mContext),jsSource,
                    null, null, R.id.frame_root_layout);
        }
        fragment.setDynamicUrlEnable(true);
        return fragment;
    }

    protected String getRenderUrl(String url) {
        String result = url;
        if (TextUtils.isEmpty(url)) {
            return result;
        }
        Uri uri = Uri.parse(url);
        String tpl = uri.getQueryParameter(EmasWeex.URL_PARAM);
        if (!TextUtils.isEmpty(tpl)) {
            Uri.Builder tplBuilder = Uri.parse(tpl).buildUpon();
            Set<String> queries = uri.getQueryParameterNames();
            if (queries != null && queries.size() > 0) {
                for (String param : queries) {
                    if (!EmasWeex.URL_PARAM.equals(param)) {
                        tplBuilder.appendQueryParameter(param, uri.getQueryParameter(param));
                    }
                }
            }
            result = tplBuilder.toString();
        }
        return result;
    }

    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "no permission");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请先申请权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

}
