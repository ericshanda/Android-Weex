package com.taobao.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.dynamic.DynamicSdk;
import com.example.luyujie.innovationcourse.BuildConfig;
import com.example.luyujie.innovationcourse.R;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsException;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.utl.ALog;
import com.taobao.demo.accs.AccsActivity;
import com.taobao.demo.agoo.AgooActivity;
import com.taobao.demo.mtop.MtopActivity;
import com.taobao.demo.orange.ui.ConfigActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class NativeDemoActivity extends BaseActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0x0003;
    private static final String TAG = "NativeDemoActivity";
    private static final boolean ADD_LAYOUT = false;  //测试补丁用，构建正常补丁时使用，增加界面布局
    private static final boolean SHOULD_CRASH = false;  //测试补丁用，构建异常Crash补丁时开启


    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (ADD_LAYOUT) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_test);
            layout.setVisibility(View.VISIBLE);
        }

        if (SHOULD_CRASH) {
            throw new RuntimeException("I am a bad patch!!!!!");
        }

        setCustomActionBar();
        setActionBarTitle(R.string.app_name);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView channelTextView = (TextView)findViewById(R.id.channel_tv);

//        textView.setText(getString(R.string.ttid) + "." + new DuplicateClazz().dup() + "_tpatch");
        /**渠道ID获取示例**/
        String ttid = getResources().getString(R.string.ttid);
        Log.i(TAG, "ttid from system api:" + ttid);
        /**渠道ID获取示例**/
        textView.setText("应用版本号：" + BuildConfig.VERSION_NAME);
        channelTextView.setText("渠道Id:" + ttid);


        try {
            ACCSClient.getAccsClient().registerDataListener("accs", new AccsAbstractDataListener() {
                @Override
                public void onData(String s, String s1, String s2, byte[] bytes, TaoBaseService.ExtraInfo extraInfo) {
                    ALog.i("NativeDemoActivity", "onData", "serviceId", s, "userId", s1, "dataId", s2, "data", new String(bytes));

                }

                @Override
                public void onBind(String s, int i, TaoBaseService.ExtraInfo extraInfo) {

                }

                @Override
                public void onUnbind(String s, int i, TaoBaseService.ExtraInfo extraInfo) {

                }

                @Override
                public void onSendData(String s, String s1, int i, TaoBaseService.ExtraInfo extraInfo) {
                    ALog.i("NativeDemoActivity", "onSendData", "serviceId", s, "dataId", s1, "errorCode", i);
                }

                @Override
                public void onResponse(String s, String s1, int i, byte[] bytes, TaoBaseService.ExtraInfo extraInfo) {
                    ALog.i("NativeDemoActivity", "onResponse", "serviceId", s, "dataId", s1, "errorCode", i, "data", new String(bytes));
                }
            });
        } catch (AccsException e) {
            ALog.e("NativeDemoActivity", "registerDataListener", e);
            e.printStackTrace();
        }
        findViewById(R.id.goto_bundle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(NativeDemoActivity.this, "com.taobao.firstbundle.FirstBundleActivity");
                    startActivity(intent);
                } catch (Throwable t) {
                    Log.e(TAG, "no firstbundle", t);
                    Toast.makeText(NativeDemoActivity.this, "Bundle不存在", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.goto_bundle1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(NativeDemoActivity.this, "com.taobao.secondbundle.SecondBundleActivity");
                    startActivity(intent);
                } catch (Throwable t) {
                    Log.e(TAG, "no secondbundle", t);
                    Toast.makeText(NativeDemoActivity.this, "Bundle不存在", Toast.LENGTH_SHORT).show();
                }

            }
        });


        final Context context = this;
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(NativeDemoActivity.this, "没有接入atlas", Toast.LENGTH_SHORT).show();


            }
        });

        findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(NativeDemoActivity.this, "com.taobao.demo.man.MANActivity");
                startActivity(intent);
            }
        });

        Button haBtn = (Button) findViewById(R.id.ha);
        haBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(NativeDemoActivity.this, HAActivity.class);
                NativeDemoActivity.this.startActivity(intent);
            }
        });

        Button dexPatchBtn = (Button) findViewById(R.id.dexpatch);
        dexPatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NativeDemoActivity.this, "没有接入atlas", Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NativeDemoActivity.this, ConfigOverviewActivity.class));
            }
        });

        Button mtopBtn = (Button) findViewById(R.id.mtop);
        mtopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(NativeDemoActivity.this, MtopActivity.class);
                startActivity(intent);
            }
        });

        Button accsBtn = (Button) findViewById(R.id.accs);
        accsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(NativeDemoActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(NativeDemoActivity.this, AccsActivity.class);
                startActivity(intent);
            }
        });

        Button orangeBtn = (Button) findViewById(R.id.orange);
        orangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(NativeDemoActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });

        Button agooBtn = (Button)findViewById(R.id.agoo);
        agooBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(NativeDemoActivity.this, AgooActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        DynamicSdk.getInstance().requestConfig();
    }

}
