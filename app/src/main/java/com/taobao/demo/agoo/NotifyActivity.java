package com.taobao.demo.agoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.taobao.agoo.BaseNotifyClickActivity;
import com.taobao.agoo.TaobaoRegister;

import org.android.agoo.common.AgooConstants;

/**
 * @author jianbo
 * @Desctription
 * @Date 2018/9/5
 * @Email kaneki.cjb@alibaba-inc.com
 */
public class NotifyActivity extends BaseNotifyClickActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String body = getIntent().getStringExtra(AgooConstants.MESSAGE_BODY);
        String from = getIntent().getStringExtra(AgooConstants.MESSAGE_SOURCE);
        if (!TextUtils.isEmpty(body) || !TextUtils.isEmpty(from)) {
            textView.setText(body + "\n msg channel:" + from);
        }
        Log.i("onResume", "body:" + body + " channel:" + from);
    }

    @Override
    public void onMessage(final Intent intent) {
        super.onMessage(intent);
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        final String id = intent.getStringExtra(AgooConstants.MESSAGE_ID);
        final String from = intent.getStringExtra(AgooConstants.MESSAGE_SOURCE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TaobaoRegister.clickMessage(NotifyActivity.this.getApplicationContext(), id, null);
                textView.setText(body + "\n msg channel:" + from);
                Log.i("onMessage", "body:" + body + " channel:" + from);
            }
        });

    }
}
