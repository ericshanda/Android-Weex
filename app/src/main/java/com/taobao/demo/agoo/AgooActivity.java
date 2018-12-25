package com.taobao.demo.agoo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luyujie.innovationcourse.R;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.TaobaoRegister;
import com.taobao.demo.EmasInit;
import com.taobao.demo.orange.SPUtil;
import com.ut.device.UTDevice;

import java.util.Map;

public class AgooActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "NAccs.AgooActivity";
    private Context context;
    private EditText aliasTV;
    private EditText accsTokenTV;
    private TextView enableTV;
    private TextView accsHostTV;
    private TextView accsStateTV;

    private Button setAliasBtn;
    private Button removeAliasBtn;
    private Button enablePushBtn;
    private Button disablePushBtn;

    public static String accsHost;
    public static String accsState = "offline";
    public static String accsToken = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agoomain);
        context = this.getApplicationContext();
        TextView v = findViewById(R.id.appk);
        v.setText(EmasInit.getInstance().getAppkey());
        v = findViewById(R.id.appsec);
        v.setText(EmasInit.getInstance().getAppSecret());
        v = findViewById(R.id.pkg);
        v.setText(getPackageName());
        aliasTV = findViewById(R.id.als);
        aliasTV.setText("Brant");
        aliasTV.clearFocus();
        enableTV = findViewById(R.id.noti);
        enableTV.setText("开启");
        v = findViewById(R.id.br);
        v.setText(Build.BRAND);
        v = findViewById(R.id.mod);
        v.setText(Build.MODEL);
        accsTokenTV = findViewById(R.id.dev);
        accsTokenTV.setText(accsToken);
        accsTokenTV.clearFocus();
        v = findViewById(R.id.sysver);
        v.setText(Build.VERSION.RELEASE);

        accsHostTV = findViewById(R.id.ho);
        accsHost = EmasInit.getInstance().getACCSDoman();
        Map<String, String> ips = EmasInit.getInstance().getIPStrategy();
        if (ips != null && ips.size() > 0) {
            String ip = ips.get(EmasInit.getInstance().getACCSDoman());
            accsHost = ip == null ? accsHost : ip;
        }
        accsHostTV.setText(accsHost);
        accsStateTV = findViewById(R.id.chan);
        accsStateTV.setText(accsState);
//        huaweiTokenTV = findViewById(R.id.ht);
//        xiaomiTokenTV = findViewById(R.id.xmt);
//        fcmTokenTV = findViewById(R.id.ft);
//        meizuTokenTV = findViewById(R.id.mzt);
//        oppoTokenTV = findViewById(R.id.oppot);

        setAliasBtn = findViewById(R.id.setAlias);
        removeAliasBtn = findViewById(R.id.removeAlias);
        enablePushBtn = findViewById(R.id.enableAgoo);
        disablePushBtn = findViewById(R.id.disableAgoo);

        setAliasBtn.setOnClickListener(this);
        removeAliasBtn.setOnClickListener(this);
        enablePushBtn.setOnClickListener(this);
        disablePushBtn.setOnClickListener(this);

        registerReceiver(mReceiver, new IntentFilter("com.taobao.accs.intent.action.CONNECTINFO"));
        //IntentFilter filter = new IntentFilter();
        //filter.addAction(DemoAgooService.NotificationClickReceiver.CLICKED_ACTION);
        //filter.addAction(DemoAgooService.NotificationClickReceiver.DISMISS_ACTION);
        //registerReceiver(mNotiReceiver, filter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        accsHostTV.setText(accsHost);
        accsStateTV.setText(accsState);
        accsTokenTV.setText(accsToken);
        aliasTV.setText(SPUtil.getString(context, "agoo", "alias", "Brant"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        //unregisterReceiver(mNotiReceiver);
    }

    //private BroadcastReceiver mNotiReceiver = new DemoAgooService.NotificationClickReceiver();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TaoBaseService.ConnectInfo info = (TaoBaseService.ConnectInfo) intent.getSerializableExtra(Constants.KEY_CONNECT_INFO);
            if (info != null && info.host != null && info.host.contains(EmasInit.getInstance().getACCSDoman())) {
                AgooActivity.accsState = info.connected ? "online" : "offline";
                accsStateTV.setText(accsState);
            }
            Log.i(TAG, "onReceive" + info == null ? "null" : info.toString());
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setAlias:
                final String alias = aliasTV.getText().toString();
                TaobaoRegister.setAlias(context, alias, new ICallback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "setAlias onSuccess");
                        toast("setAlias onSuccess");
                        SPUtil.putString(context, "agoo", "alias", alias);
                    }

                    @Override
                    public void onFailure(String errCode, String errMsg) {
                        String msg = "setAlias onFailure, code:" + errCode + "errMsg:" + errMsg;
                        Log.i(TAG, msg);
                        toast(msg);
                    }
                });
                break;
            case R.id.removeAlias:
                TaobaoRegister.removeAlias(context, new ICallback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "removeAlias onSuccess");
                        toast("removeAlias onSuccess");
                    }

                    @Override
                    public void onFailure(String errCode, String errMsg) {
                        String msg = "removeAlias onFailure, code:" + errCode + "errMsg:" + errMsg;
                        Log.i(TAG, msg);
                        toast(msg);
                    }
                });
                break;
            case R.id.enableAgoo:
                TaobaoRegister.bindAgoo(context, new ICallback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "bindAgoo onSuccess");
                        handler.sendEmptyMessage(R.id.enableAgoo);
                        toast("bindAgoo onSuccess");
                    }

                    @Override
                    public void onFailure(String errCode, String errMsg) {
                        String msg = "bindAgoo onFailure, code:" + errCode + " errMsg:" + errMsg;
                        Log.i(TAG, msg);
                        toast(msg);
                    }
                });
                break;
            case R.id.disableAgoo:
                TaobaoRegister.unbindAgoo(context, new ICallback() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "unbindAgoo onSuccess");
                        handler.sendEmptyMessage(R.id.disableAgoo);
                        toast("unbindAgoo onSuccess");
                    }

                    @Override
                    public void onFailure(String errCode, String errMsg) {
                        String msg = "unbindAgoo onFailure, code:" + errCode + " errMsg:" + errMsg;
                        Log.i(TAG, msg);
                        toast(msg);
                    }
                });
                break;
            default:
                break;
        }
    }


    private void toast(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case R.id.enableAgoo:
                    enableTV.setText("开启");
                    break;
                case R.id.disableAgoo:
                    enableTV.setText("关闭");
                    break;
                default:
                        break;
            }
        }
    };
}
