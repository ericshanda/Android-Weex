package com.taobao.demo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.luyujie.innovationcourse.R;
import com.taobao.demo.webview.WebViewSampleActivity;

import com.taobao.demo.weex.WeexActivity;

public class LandingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setCustomActionBar();
        setActionBarTitle(R.string.title);
        hideBack();
        showScan();

        findViewById(R.id.btn_native).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, NativeDemoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_weex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, WeexActivity.class);
                intent.setData(Uri.parse("http://cdn.emas-poc.com/material/yanpeicpf/index.html?_wx_tpl=http://cdn.emas-poc.com/app/yanpeicpf-bbb/pages/index/entry.js"));
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_h5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://cdn.emas-poc.com/app/yanpeicpf-aaa/index.html"));
                intent.setPackage(getPackageName());
                intent.addCategory("com.emas.android.intent.category.HYBRID");
                if (null != intent.resolveActivity(getPackageManager())) {
                    startActivity(intent);
                }
            }
        });

        int scaffoldType = getIntent().getIntExtra("scaffoldType", 1);
//        if ((scaffoldType & 1) != 1) {
//            findViewById(R.id.btn_native).setVisibility(View.GONE);
//        }
        if ((scaffoldType & 2) != 2) {
            findViewById(R.id.btn_weex).setVisibility(View.GONE);
        }
        if ((scaffoldType & 4) != 4) {
            findViewById(R.id.btn_h5).setVisibility(View.GONE);
        }
    }
}
