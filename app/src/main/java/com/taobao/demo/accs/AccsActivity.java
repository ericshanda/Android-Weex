package com.taobao.demo.accs;

import java.text.SimpleDateFormat;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.luyujie.innovationcourse.R;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsException;
import com.taobao.demo.EmasInit;

public class AccsActivity extends AppCompatActivity {
    TextView responseText;
    ScrollView scrollRequestView;

    TextView dataText;
    ScrollView scrollDataView;

    Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accs);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Accs演示");

        responseText = findViewById(R.id.response);
        scrollRequestView = findViewById(R.id.scroll_request);
        dataText = findViewById(R.id.data);
        scrollDataView = findViewById(R.id.scroll_data);

        TestAccsService.setIAccsResponse(new TestAccsService.IAccsResponse() {
            @Override
            public void onResponse(final int errorCode, final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(responseText.getText().toString())) {
                            responseText.append("\n");
                        }
                        responseText.append(">>[SERVER RESPONSE]:\n");
                        if (errorCode == 200) {
                            try {
                                Long timestamp = Long.parseLong(response);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date = format.format(timestamp);
                                responseText.append(date);
                            } catch (Throwable t) {
                                responseText.append(t.getMessage());
                            }
                        } else {
                            responseText.append("error:" + errorCode);
                        }
                        scrollRequestView.post(new Runnable() {
                            public void run() {
                                scrollRequestView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                });
            }

            @Override
            public void onData(final String serviceId, final String dataId, final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(dataText.getText().toString())) {
                            dataText.append("\n");
                        }
                        dataText.append(">>[RECEIVE DATA]:\n");
                        dataText.append("   dataId:" + dataId + "\n");
                        dataText.append("   serviceId:" + serviceId + "\n");
                        dataText.append("   payload:" + data);
                        scrollDataView.post(new Runnable() {
                            public void run() {
                                scrollDataView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                });
            }
        });

        findViewById(R.id.request).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest(null, EmasInit.SERVICE_ID,
                        String.valueOf(mRandom.nextInt(99999)).getBytes(), null);
                    ACCSClient.getAccsClient(AccsClientConfig.DEFAULT_CONFIGTAG).sendRequest(accsRequest);
                } catch (AccsException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.get_config:
                startActivity(new Intent(this, ConfigActivity.class));
                break;
            default:
                break;
        }
        return true;
    }
}
