package com.taobao.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.luyujie.innovationcourse.R;
import com.taobao.demo.config.EMASInfo;

public class ConfigOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_overview);
        EMASInfo emasInfo = Utils.parseEmasInfo(this);
        if (emasInfo != null) {
            ((TextView)findViewById(R.id.appkey)).setText(emasInfo.AppKey);
            ((TextView)findViewById(R.id.appSecret)).setText(emasInfo.AppSecret);
            ((TextView)findViewById(R.id.channelID)).setText(emasInfo.ChannelID);
            ((TextView)findViewById(R.id.useHttp)).setText(emasInfo.UseHTTP ? "是" : "否");
            ((TextView)findViewById(R.id.accsDomain)).setText(emasInfo.ACCS.Domain);
            ((TextView)findViewById(R.id.mtopDomain)).setText(emasInfo.MTOP.Domain);
            ((TextView)findViewById(R.id.zcacheUrl)).setText(emasInfo.ZCache.URL);
            ((TextView)findViewById(R.id.haBucketName)).setText(emasInfo.HA.OSSBucketName);
            ((TextView)findViewById(R.id.haAdash)).setText(emasInfo.HA.UniversalHost);
            ((TextView)findViewById(R.id.haRsa)).setText(emasInfo.HA.RSAPublicKey);
            ((TextView)findViewById(R.id.ipStrategy)).setText(emasInfo.Network.toString());
            ((TextView)findViewById(R.id.versionCode)).setText(String.valueOf(packageCode(this)));
            ((TextView)findViewById(R.id.versionName)).setText(packageName(this));

        }
    }

    public static int packageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }
}
