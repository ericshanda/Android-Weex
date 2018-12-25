package com.taobao.demo.accs;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.luyujie.innovationcourse.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.taobao.demo.update.MainScanActivity;

public class ConfigActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 0;
    private static final int REQUEST_CODE = 1;
    private static final String TAG = ConfigActivity.class.getSimpleName();

    TextView configText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accs_config);

        configText = findViewById(R.id.config);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("读取配置");
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.scan).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestPermission()) {
                    //startActivity(new Intent(this, CaptureActivity.class));
                    IntentIntegrator integrator = new IntentIntegrator(ConfigActivity.this);
                    integrator.setPrompt("请扫描"); //底部的提示文字，设为""可以置空
                    integrator.setCameraId(0); //前置或者后置摄像头
                    integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
                    integrator.setCaptureActivity(MainScanActivity.class);
                    integrator.initiateScan();
                    Intent intent = new Intent(ConfigActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

        findViewById(R.id.save).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String config = configText.getText().toString();
                //{"appkey":"accs_4272_mock","appsecret":"257461a8005f538382640d4894dd193a04d18e1b4a7a5ee214b6d660778d3943"}
                if (!TextUtils.isEmpty(config)) {
                    ScanAccsConfig accsConfig = JSON.parseObject(config, ScanAccsConfig.class);
                    if (accsConfig != null && accsConfig.verify()) {
                        SharedPreferences sp = getSharedPreferences("emas_accs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("appkey", accsConfig.appkey);
                        editor.putString("appsecret", accsConfig.appsecret);
                        boolean result = editor.commit();
                        if (result) {
                            Toast.makeText(ConfigActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    Toast.makeText(this, "请打开摄像头权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String content = result.getContents();
        if(content == null) {
            Log.d(TAG, "scan result null, return");
            Toast.makeText(this, "scan result null", Toast.LENGTH_LONG).show();
            configText.setVisibility(View.INVISIBLE);
            return;
        }
        Log.d(TAG, "Scanned: " + result.getContents());
        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

        configText.setVisibility(View.VISIBLE);
        configText.setText(content);
    }

    public static class ScanAccsConfig implements Serializable {
        public String appkey;
        public String appsecret;

        public boolean verify() {
            if (TextUtils.isEmpty(appkey) || TextUtils.isEmpty(appsecret)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ScanAccsConfig{");
            sb.append("appkey='").append(appkey).append('\'');
            sb.append(", appsecret='").append(appsecret).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
