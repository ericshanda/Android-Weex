package com.taobao.demo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.luyujie.innovationcourse.R;
import com.emas.weex.inspector.WeexInspectorManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.taobao.demo.update.MainScanActivity;
import com.taobao.demo.weex.WeexActivity;

public class BaseActivity extends AppCompatActivity {

    protected static final int CAMERA_PERMISSION_REQUEST_CODE = 0x0002;
    protected static final int STORAGE_PERMISSION_REQUEST_CODE = 0x0003;
    protected static final String TAG = "BaseActivity";
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.action_bar, null);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setCustomView(mActionBarView, lp);
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            if (Build.VERSION.SDK_INT >= 21) {
                mActionBar.setElevation(0);
            }
        }

        if (mActionBarView.getParent() instanceof Toolbar) {
            Toolbar parent = (Toolbar) mActionBarView.getParent();
            parent.setContentInsetsAbsolute(0, 0);
        }

        findViewById(R.id.image_back).setOnClickListener(mBackOnClickListener);
        setActionBarIcon(R.drawable.scan, mScanOnClickListener);
    }

    protected View.OnClickListener mScanOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.e(TAG, "scan");
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "no permission");
                if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, Manifest.permission.CAMERA)) {
                    Toast.makeText(mContext, "please give me the permission", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                }
            } else {
                Log.e(TAG, "got permission");
                //startActivity(new Intent(this, CaptureActivity.class));
                startBarcodeScan("请扫描二维码");
            }

        }

    };

    protected View.OnLongClickListener mScanLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final EditText editText = new EditText(BaseActivity.this);
            AlertDialog.Builder inputDialog =
                    new AlertDialog.Builder(BaseActivity.this);
            inputDialog.setTitle("输入/粘贴网址").setView(editText);
            inputDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = editText.getText().toString().trim();
                            handleScanCodeResult(url);
                        }
                    }).show();
            return true;
        }
    };

    protected View.OnClickListener mBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    protected void setActionBarTitle(int resId) {
        TextView title = findViewById(R.id.title);
        if (title != null) {
            title.setText(resId);
        }
    }

    protected void setActionBarIcon(int resId, View.OnClickListener listener) {
        ImageView imageView = findViewById(R.id.image_left);
        if (imageView != null) {
            imageView.setBackgroundResource(resId);
            imageView.setOnClickListener(listener);
            imageView.setOnLongClickListener(mScanLongClickListener);
        }
    }

    protected void startBarcodeScan(String hint) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt(hint); //底部的提示文字，设为""可以置空
        integrator.setCameraId(0); //前置或者后置摄像头
        integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
        integrator.setCaptureActivity(MainScanActivity.class);
        integrator.initiateScan();
    }

    protected void hideBack() {
        findViewById(R.id.image_back).setVisibility(View.GONE);
    }

    protected void showScan() {
        findViewById(R.id.image_left).setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startBarcodeScan("请扫描二维码");
                } else {
                    Toast.makeText(mContext, "扫码调试需要相机权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(TAG, "Cancelled");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else if (result.getContents().contains("dynamicdeploy") || result.getContents().contains("dexpatchdeploy")) {
                try {
                    com.taobao.tao.update.Updater.getInstance(BaseActivity.this).triggerEmasDynamicDeployment(result.getContents());
                } catch (Throwable t) {
                    Log.e(TAG, "invalid update data", t);
                    Toast.makeText(this, "invalid update data", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.d(TAG, "Scanned: " + result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                handleScanCodeResult(result.getContents());
            }

            Log.d(TAG, TextUtils.isEmpty(result.getContents()) ? "empty" : result.getContents());
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }


    private void handleScanCodeResult(String result) {
        Uri uri = Uri.parse(result);
        if (uri == null) {
            Log.e(TAG, "scan result null, return");
        } else {
            if (WeexInspectorManager.startInspector(uri, mContext)) {
                Toast.makeText(this, "连接调试模式成功", Toast.LENGTH_SHORT).show();
                startBarcodeScan("请再次扫描调试界面预览二维码以调试");
            } else {

                String ext = result.substring(result.lastIndexOf(".") + 1);
                if (ext.equals("wx") || ext.equals("we") || ext.equals("js") || !TextUtils.isEmpty(uri.getQueryParameter("_wx_tpl"))) {
                    Intent activityIntent = new Intent(this, WeexActivity.class);
                    activityIntent.setData(uri);
                    activityIntent.setPackage(getPackageName());
                    activityIntent.setAction("com.taobao.android.intent.action.WEEX");
                    startActivity(activityIntent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage(getPackageName());
                    intent.addCategory("com.emas.android.intent.category.HYBRID");
                    startActivity(intent);
                }

            }
        }
    }

}
