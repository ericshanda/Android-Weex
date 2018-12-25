package com.taobao.demo.orange.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.luyujie.innovationcourse.R;
import com.ta.utdid2.device.UTDevice;
import com.taobao.orange.util.FileUtil;

public class ConfigActivity extends AppCompatActivity {
    private TextView utdidText;
    private TextView appVersionText;
    private TextView systemVersionText;
    private TextView manufacturerText;
    private TextView brandText;
    private TextView modelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        initViews();
    }

    private void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        utdidText = findViewById(R.id.utdid);
        utdidText.setText(UTDevice.getUtdid(this));

        String appVersion = "1.0.0";
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        appVersionText = findViewById(R.id.app_version);
        appVersionText.setText(appVersion);
        systemVersionText = findViewById(R.id.system_version);
        systemVersionText.setText(Build.VERSION.RELEASE);
        manufacturerText = findViewById(R.id.manufacturer);
        manufacturerText.setText(Build.MANUFACTURER);
        brandText = findViewById(R.id.brand);
        brandText.setText(Build.BRAND);
        modelText = findViewById(R.id.model);
        modelText.setText(Build.MODEL);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_candidate:
                startActivity(new Intent(this, CandidateActivity.class));
                break;
            default:
                break;
        }
        return true;
    }*/

    public void debugConfig(View view) {
        startActivity(new Intent(this, DebugActivity.class));
    }

    public void demoConfig(View view) {
        startActivity(new Intent(this, DemoActivity.class));
    }

    public void clearOrange(View view) {
        FileUtil.clearCacheFile();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /*public void getAccs(View view) {
        try {
            Map<String, Boolean> result = ACCSClient.getAccsClient(AccsClientConfig.DEFAULT_CONFIGTAG).getChannelState();
            Toast.makeText(this, "status:" + result, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}