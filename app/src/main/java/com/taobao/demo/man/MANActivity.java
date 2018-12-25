package com.taobao.demo.man;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.android.emas.man.EMASMANService;
import com.alibaba.android.emas.man.EMASMANServiceFactory;
import com.alibaba.android.emas.man.hit.EMASMANCustomHitBuilder;
import com.alibaba.android.emas.man.hit.EMASMANPageHitBuilder;
import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.adapter.AliHaConfig;
import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.adapter.Sampling;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.example.luyujie.innovationcourse.R;

public class MANActivity extends AppCompatActivity {

    private EMASMANService manService;
    private String testUserNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man);


        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        Button button4 = (Button)findViewById(R.id.button4);
        Button button5 = (Button)findViewById(R.id.button5);
        Button button6 = (Button)findViewById(R.id.button6);

        manService = EMASMANServiceFactory.getMANService();

        // 页面辅助埋点
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MANActivity.this, TestPageActivity.class);
                startActivity(intent);
            }
        });

        // 页面基础埋点
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EMASMANPageHitBuilder pageHitBuilder = new EMASMANPageHitBuilder("testPage");
                pageHitBuilder.setReferPage("testReferPage");
                pageHitBuilder.setDurationOnPage(1234);
                pageHitBuilder.setProperty("pageKey1", "pageValue1");
                Map<String, String> properties = new HashMap<>();
                properties.put("pageKey2", "pageValue2");
                pageHitBuilder.setProperties(properties);
                manService.send(pageHitBuilder.build());
            }
        });

        // 自定义埋点
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EMASMANCustomHitBuilder customHitBuilder = new EMASMANCustomHitBuilder("testEvent");
                customHitBuilder.setEventPage("testPage");
                customHitBuilder.setDurationOnEvent(1234);
                customHitBuilder.setProperty("key1", "value1");
                Map<String, String> properties = new HashMap<>();
                customHitBuilder.setProperties(properties);
                manService.send(customHitBuilder.build());
            }
        });

        // 用户注册
        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                testUserNick = "userNick-" + new Date().getTime();
                manService.userRegister(testUserNick);
                showAlert("注册用户名: " + testUserNick);
            }
        });

        // 用户登录
        button6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                manService.userLogin(testUserNick, testUserNick);
            }
        });

    }

    private void showAlert(String alertStr) {
        Toast.makeText(getApplicationContext(), alertStr, Toast.LENGTH_SHORT).show();
    }
}
