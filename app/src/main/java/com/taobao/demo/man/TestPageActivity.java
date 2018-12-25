package com.taobao.demo.man;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.android.emas.man.hit.EMASMANPageHitHelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.luyujie.innovationcourse.R;

public class TestPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        Map<String, String> properties = new HashMap<>();
        properties.put("pageKey1", "pageValue1");
        properties.put("pageKey2", "pageValue2");
        EMASMANPageHitHelper.getInstance().updatePageProperties(properties);
    }
}
