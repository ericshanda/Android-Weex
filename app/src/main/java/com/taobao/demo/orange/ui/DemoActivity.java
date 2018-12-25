package com.taobao.demo.orange.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luyujie.innovationcourse.R;
import com.taobao.orange.OConfigListener;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.util.OLog;

import java.util.Map;

public class DemoActivity extends AppCompatActivity {
    private static final String TAG = "DemoActivity";

    private static final String NAMESPACE_THEME = "theme";
    private static final String KEY_THEME_NAME = "theme_name";
    private static final String KEY_THEME_COLOR = "theme_color";
    private static final String DEFAULT_THEME_NAME = "unknow";
    private static final String DEFAULT_THEME_COLOR = "#373D41";

    private TextView themeNameText;
    private TextView themeColorText;
    private Button playBtn;

    private OConfigListener mListener = new OConfigListener() {
        @Override
        public void onConfigUpdate(String namespace, Map<String, String> args) {
            OLog.d(TAG, "onConfigUpdate", "namespace", namespace, "args", args);
            if (NAMESPACE_THEME.equals(namespace)) {
                updateTheme();
            } else if (FeatureActivity.NAMESPACE_FEATURE.equals(namespace)) {
                updateFeature();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        themeNameText = findViewById(R.id.theme_name);
        themeColorText = findViewById(R.id.theme_color);
        playBtn = findViewById(R.id.play);
        updateTheme();
        updateFeature();
        OrangeConfig.getInstance().registerListener(new String[]{NAMESPACE_THEME, FeatureActivity.NAMESPACE_FEATURE}, mListener);
    }

    private void updateTheme() {
        final String themeName = OrangeConfig.getInstance().getConfig(NAMESPACE_THEME, KEY_THEME_NAME, DEFAULT_THEME_NAME);
        final String themeColor = OrangeConfig.getInstance().getConfig(NAMESPACE_THEME, KEY_THEME_COLOR, DEFAULT_THEME_COLOR);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                themeNameText.setText(String.format("%s = %s", KEY_THEME_NAME, themeName));
                try {
                    themeColorText.setBackgroundColor(Color.parseColor(themeColor));
                } catch (Throwable t) {
                    OLog.e(TAG, "initViews", t);
                }
            }
        });
    }

    private void updateFeature() {
        final String featureEnable = OrangeConfig.getInstance().getConfig(FeatureActivity.NAMESPACE_FEATURE,
                FeatureActivity.KEY_FEATURE_ENABLE, "true");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean enable = Boolean.parseBoolean(featureEnable);
                    playBtn.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
                } catch (Throwable t) {
                    OLog.e(TAG, "initViews", t);
                }
            }
        });
    }

    public void play(View view) {
        Intent intent = new Intent(this, FeatureActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OrangeConfig.getInstance().unregisterListener(new String[]{NAMESPACE_THEME, FeatureActivity.NAMESPACE_FEATURE});
    }
}
