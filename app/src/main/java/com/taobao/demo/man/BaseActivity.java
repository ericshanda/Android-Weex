package com.taobao.demo.man;

import com.alibaba.android.emas.man.hit.EMASMANPageHitHelper;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMASMANPageHitHelper.getInstance().pageAppear(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EMASMANPageHitHelper.getInstance().pageDisAppear(this);
    }
}
