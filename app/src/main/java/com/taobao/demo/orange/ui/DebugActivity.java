package com.taobao.demo.orange.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.luyujie.innovationcourse.R;
import com.taobao.demo.orange.adapter.ConfigAdapter;
import com.taobao.orange.ConfigCenter;
import com.taobao.orange.inner.OUpdateListener;
import com.taobao.orange.model.ConfigDO;

public class DebugActivity extends AppCompatActivity {
    RecyclerView configRecyclerView;
    TextView configContentText;

    private int curPosition = -1;
    private ConfigAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        initViews();

        ConfigCenter.getInstance().setUpdateListener(new OUpdateListener() {
            @Override
            public void invoke() {
                updateRecycleView();
            }
        });

        /*String val1 = OrangeConfig.getInstance().getConfig("test_null", "key", "default");
        OLog.e("wuer", "test", "val1", val1);
        String val2 = OrangeConfig.getInstance().getConfig("test_null", "key1", "default");
        OLog.e("wuer", "test", "val2", val2);

        String val3 = OrangeConfig.getInstance().getCustomConfig("custom_config", "default");
        OLog.e("wuer", "test", "val3", val3);
        String val4 = OrangeConfig.getInstance().getCustomConfig("custom_config2", "default");
        OLog.e("wuer", "test", "val4", val4);*/
    }

    private void initViews() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        configRecyclerView = findViewById(R.id.config_list);
        configContentText = findViewById(R.id.config_content);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new ConfigAdapter(ConfigCenter.getInstance().getConfigs());
        adapter.setOnItemClickListener(new ConfigAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                curPosition = position;
                ConfigDO config = adapter.get(position);
                if (config != null) {
                    configContentText.setText(JSON.toJSONString(config, SerializerFeature.PrettyFormat));
                }
            }
        });
        configRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        configRecyclerView.setLayoutManager(layoutManager);
        configRecyclerView.setAdapter(adapter);
    }

    public void updateRecycleView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.update(ConfigCenter.getInstance().getConfigs());

                if (curPosition != -1) {
                    ConfigDO config = adapter.get(curPosition);
                    configContentText.setText(JSON.toJSONString(config, SerializerFeature.PrettyFormat));
                }
            }
        });
    }
}
