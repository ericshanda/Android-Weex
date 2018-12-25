package com.taobao.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.bizerrorreporter.BizErrorReporter;
import com.alibaba.ha.bizerrorreporter.module.AggregationType;
import com.alibaba.ha.bizerrorreporter.module.BizErrorModule;
import com.alibaba.motu.tbrest.SendService;
import com.alibaba.motu.tbrest.utils.DeviceUtils;
import com.alibaba.mtl.appmonitor.AppMonitor;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import com.example.luyujie.innovationcourse.BuildConfig;
import com.example.luyujie.innovationcourse.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import crashreporter.motu.alibaba.com.tbcrashreporter4androiddemo.NativeCrashTest;

/**
 * Created by qiulibin on 2017/12/26.
 *
 * ha demo activity
 *
 */

public class HAActivity extends Activity {


    private static Activity activity = null;

    private TextView appKeyText = null;
    private TextView appVersionText = null;
    private TextView isHttpOrHttps = null;
    private TextView isLinkText = null;
    private EditText hostName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hademo);

        appKeyText = (TextView)findViewById(R.id.appKeyId);
        appVersionText = (TextView)findViewById(R.id.appVersionId);
        isHttpOrHttps = (TextView) findViewById(R.id.httpOrHttps);
        isLinkText = (TextView) findViewById(R.id.isLink);
        hostName = (EditText) findViewById(R.id.host);

        final EmasInit emas = EmasInit.getInstance();
        appKeyText.setText(emas.mAppkey);
        appVersionText.setText(BuildConfig.VERSION_NAME);
        if (emas.mUseHttp){
            isHttpOrHttps.setText("http");
        } else {
            isHttpOrHttps.setText("https");
        }
        hostName.setText(emas.mHAUniversalHost);

        //http 切换
        Button httpBtn = (Button) findViewById(R.id.http);
        httpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AliHaAdapter.getInstance().openHttp(true);
                emas.mUseHttp = true;
                //更新到界面
                isHttpOrHttps.setText("http");
                Toast.makeText(HAActivity.this, "成功切换到HTTP",  Toast.LENGTH_SHORT).show();
            }
        });

        //https 切换
        Button httpsBtn = (Button) findViewById(R.id.https);
        httpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AliHaAdapter.getInstance().openHttp(false);
                emas.mUseHttp = false;
                //更新到界面
                isHttpOrHttps.setText("https");
                Toast.makeText(HAActivity.this, "成功切换到HTTPS",  Toast.LENGTH_SHORT).show();
            }
        });

        //变更域名
        Button changeHostBtn = (Button) findViewById(R.id.changeHost);
        changeHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newHost = hostName.getText().toString();
                emas.mHAUniversalHost = newHost;
                AliHaAdapter.getInstance().changeHost(newHost);
                Toast.makeText(HAActivity.this, "成功切换到HOST：" + newHost,  Toast.LENGTH_SHORT).show();
            }
        });

        //检测连通性
        Button checkLinkBtn = (Button) findViewById(R.id.checkLink);
        checkLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread th = new Thread() {
                    @Override
                    public void run(){
                        String content = "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";
                        Boolean isTrue = SendService.getInstance().sendRequest(null,
                                System.currentTimeMillis(),
                                "ALIHA",
                                61004,
                                "telescope",
                                content,
                                null,
                                null);
                        if (isTrue){
                            isLinkText.setText("true");
                            Toast.makeText(HAActivity.this, "成功发送一条数据：eventId=61004",  Toast.LENGTH_SHORT).show();
                        } else {
                            isLinkText.setText("false");
                            Toast.makeText(HAActivity.this, "发送一条数据失败：eventId=61004",  Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                th.start();
            }
        });

        //native crash test
        final NativeCrashTest nativeCrashTest = new NativeCrashTest();
        // java crash
        Button javaCrashBtn = (Button) findViewById(R.id.javaCrash);
        javaCrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throw new NullPointerException();
            }
        });

        // native crash
        Button nativeCrashBtn = (Button) findViewById(R.id.nativeCrash);
        nativeCrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nativeCrashTest.TestNativeCrashMethod(1);
            }
        });

        //卡顿
        Button stuckCrashBtn = (Button) findViewById(R.id.stuck);
        stuckCrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Thread.sleep(20*1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //自定义错误
        Button appmonitorBtn = (Button) findViewById(R.id.appmonitor);
        appmonitorBtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View view) {

                  //alarm
                  // 提交成功事件
                  AppMonitor.Alarm.commitSuccess("Page_system", "hotPatch");
                  // 提交失败事件
                  AppMonitor.Alarm.commitFail("Page_system", "hotPatch", "code123", "request fail");

                  //count
                  AppMonitor.Counter.commit("Page_system", "appEnterBackground", 1);
                  AppMonitor.Counter.commit("ImgSDK", "downloadImg", 5);

                  //stat
                  //定义维度集合
                  DimensionSet dimensionSet = DimensionSet.create();
                  //添加维度
                  dimensionSet.addDimension("isSpdy");
                  //定义指标集合
                  MeasureSet measureSet = MeasureSet.create();
                  //添加指标，默认取值范围大于等于0
                  measureSet.addMeasure("a");
                  measureSet.addMeasure("b");
                  measureSet.addMeasure("c");

                  AppMonitor.register("network_poc","responseTime",measureSet,dimensionSet);
                  for (int i = 0; i < 20; i++){
                      //增加setValue添加维度、指标
                      DimensionValueSet dimensionValues= DimensionValueSet.create().setValue("isSpdy", ""+i);
                      MeasureValueSet measureValues= MeasureValueSet.create();
                      measureValues.setValue("a",100+i);
                      measureValues.setValue("b",200+i);
                      measureValues.setValue("c",300+i);

                      //多维度多指标，最通用
                      AppMonitor.Stat.commit("network_poc", "responseTime", dimensionValues, measureValues);
                  }

                  Toast.makeText(HAActivity.this, "发送计数，告警，性能埋点（200）完成",  Toast.LENGTH_SHORT).show();
              }
        });

        //打 tlog日志
        Button remoteDebugBtn = (Button) findViewById(R.id.logPrint);
        remoteDebugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TAG = "HaDemo";
                String MODEL = "HAActivity";

                for (int i = 0; i < 10; ++i){
                    String uuid = getRandomNum();
                    AliHaAdapter.getInstance().tLogService.loge(MODEL,TAG,i+"" + uuid);
                }

                for (int i = 0; i < 10; ++i){
                    String uuid = getRandomNum();
                    AliHaAdapter.getInstance().tLogService.loge(MODEL,TAG,"fenchao" + i+"hahh" + uuid);
                }

                InputStreamReader isr = null;
                try {
                    isr = new InputStreamReader(getAssets().open("tlog.txt"),"UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while((line = br.readLine()) != null) {
                        int index = line.indexOf(':');
                        AliHaAdapter.getInstance().tLogService.loge("TlogModel", line.substring(0, index), line.substring(index + 1));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (isr != null) {
                        try {
                            isr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Toast.makeText(HAActivity.this, "打日志完成，100条，model = " + MODEL + " tag = " + TAG,
                        Toast.LENGTH_SHORT).show();
            }
        });

        //主动上报日志
        Button logUploadBtn = (Button) findViewById(R.id.logUpload);
        logUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //上传
                startLog(getApplication().getApplicationContext());

                Toast.makeText(HAActivity.this, "主动上报今天的日志完成，当前设备ID为：" + DeviceUtils.getUtdid(getApplication().getApplicationContext()),
                        Toast.LENGTH_SHORT).show();
            }
        });


        //发送event时间
        Button telescopeBtn = (Button) findViewById(R.id.telescope);
        telescopeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contain = "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";

                for (int i = 0; i < 10; ++i){
                    SendService.getInstance().sendRequestAsyn(null,
                            System.currentTimeMillis(),
                            "ALIHA",
                            61004,
                            "telescope",
                            contain,
                            null,
                            null);
                }

                Toast.makeText(HAActivity.this, "上传完成10条性能事件数据", Toast.LENGTH_SHORT).show();
            }
        });

        //内存泄露检测
        Button memLeakBtn = (Button)findViewById(R.id.leak);
        memLeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contain = "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";

                for (int i = 0; i < 3; ++i){
                    SendService.getInstance().sendRequestAsyn(null,
                            System.currentTimeMillis(),
                            "ALIHA",
                            61004,
                            "telescope",
                            contain,
                            null,
                            null);
                }

                 //这里虚晃一招， 发送假数据
                Toast.makeText(HAActivity.this, "发送内存泄露数据完成", Toast.LENGTH_SHORT).show();
            }
        });
        //这里造内存泄露
        if (activity == null){
            activity = this;
        }

        //js error 发送
        Button jsErrorBtn = (Button) findViewById(R.id.jsError);
        jsErrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    for (int i = 0; i < 10; ++i){
                        sendJsError(getApplication().getApplicationContext());
                    }

                    Toast.makeText(HAActivity.this, "已发送10条js error",
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // 网络分析监控报警
        Button networkMonitorBtn = (Button) findViewById(R.id.networkMonitor);
        networkMonitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            networkMonitor();
                        }
                    }).start();
                }
                Toast.makeText(HAActivity.this, "网络请求完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //上传
    public void startLog(final Context context){
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                //LogFileUploadManager logFileUploadManager = new LogFileUploadManager(context);
                //logFileUploadManager.uploadWithFilePrefix(null,"feedback","tlog",null);
            }
        };

        Timer timer = new Timer();
        //time为Date类型：在指定时间执行一次。
        timer.schedule(task, 3 * 1000);
    }

    //随机数
    public String getRandomNum(){
        try {
            UUID uuid = UUID.randomUUID();
            return uuid.toString().replace("-", "");
        }catch (Exception e){
            Log.w(AliHaAdapter.TAG,"get random num failure",e);
        }
        return null;
    }

    /**
     * 发送 weex js error 错误日志
     * @param context
     */
    public void sendJsError(Context context){
        BizErrorModule module = new BizErrorModule();

        /**
         * 日志聚合类型，必填，目前有两种聚合类型
         * 按字符串内容聚合：相同exceptionCode的日志聚合到一起
         * 按堆栈聚合：堆栈特征（throwable）相同的日志聚合到一起
         */
        module.aggregationType = AggregationType.CONTENT;
        /**
         * 必填，这个名字找凤巢申请（或自定义好告诉凤巢，一般为大写字符串组成）
         */
        module.businessType = "WEEX_ERROR";
        /**
         * 错误码，按内容聚合时，该参数必填
         */
        module.exceptionCode = "market.m.taobao.com/apps/market/detailrax/usertalk-detail.html?wh_weex=true&itemId=560617457198&userId=0&showId=231173101&sellerId=2336710263&pre_item_id=560617457198&_wx_f_=1&_p_f_22=1";
        /**
         * 选填
         * 错误标识
         */
        module.exceptionId = "-2013";
        /**
         * 选填
         * 错误明细：按内容聚合的日志，错误明细极其重要
         */
        module.exceptionDetail = "Exception: ReferenceError: Can't find variable: __weex_config__";
        /**
         * 选填
         * 一般为业务sdk的版本号
         */
        module.exceptionVersion = "1.0.0.0";
        /**
         * 选填
         * 当前线程
         */
        module.thread = Thread.currentThread();
        /**
         * 当按堆栈聚合时，该参数必填
         */
        module.throwable = null;
        /**
         * 选填
         * 额外信息，可自定义选择，其他额外信息字段雷同，不做描述了
         */
        module.exceptionArg1 = null;

        //发送业务错误日志
        BizErrorReporter.getInstance().send(context,module);
    }

    // 发起一个网络请求(HTTPDNS域名解析)，计算各个阶段(端上逻辑、网络传输、服务端处理)耗时
    private void networkMonitor() {
        HttpURLConnection conn = null;
        InputStream in = null;
        BufferedReader streamReader = null;
        // 端上处理时间随机生成
        double deviceTime = Math.random() * 1000;
        long curTime = System.currentTimeMillis();
        double serverTime = 0;
        double sendTime = 0;
        long totalTime = 0;
        boolean isFailed = false;
        boolean timeout = false;
        String errorMsg = null;
        String accountId = "128633";
        String domainName = "www.aliyun.com";
        String url = "http://203.107.1.1:80/" + accountId + "/d?host=" + domainName;
        try {

            conn = (HttpURLConnection) new URL(url).openConnection();
            if (conn.getResponseCode() == 200) {
                in = conn.getInputStream();
                streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = streamReader.readLine()) != null) {
                    sb.append(line);
                }
                Log.e("TEST", sb.toString());

                totalTime = System.currentTimeMillis() - curTime;
            } else {
                totalTime = System.currentTimeMillis() - curTime;
            }
            conn.disconnect();
            conn = null;

            serverTime = totalTime * Math.random();
            sendTime = totalTime - serverTime;
        } catch (IOException e) {
            e.printStackTrace();
            isFailed = true;
            errorMsg = e.getLocalizedMessage();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            try {
                if (in != null) {
                    in.close();
                }
                if (streamReader != null) {
                    streamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.e("TEST", "deviceTime:" + deviceTime + ", sendTime:" + sendTime + ", serverTime:" + serverTime);
        if (deviceTime > 500 || sendTime > 100 || serverTime > 100) {
            timeout = true;
        }

        Log.e("TEST", "timeout:" + timeout + ", isFailed:" + isFailed);
                // 不符合预期，tlog上报
        if (timeout || isFailed) {
            String msg = "";
            if (timeout) {
                msg += "netowrk request timeout, deviceTime:" + deviceTime + ", sendTime:" + sendTime + ", serverTime:" + serverTime + "\n for url" + url;
            }

            if (isFailed) {
                msg += "networkRequest failed for:" + errorMsg +
                    "\n" + "for url:" + url;
            }
            AliHaAdapter.getInstance().tLogService.loge("networkMonitor", "networkMonitor", msg);
        }

        //stat
        //定义维度集合
        DimensionSet dimensionSet = DimensionSet.create();
        //添加维度
        dimensionSet.addDimension("result");
        dimensionSet.addDimension("timeout");
        //定义指标集合
        MeasureSet measureSet = MeasureSet.create();
        //添加指标，默认取值范围大于等于0
        measureSet.addMeasure("deviceTime");
        measureSet.addMeasure("sendTime");
        measureSet.addMeasure("backendTime");
        measureSet.addMeasure("count");

        AppMonitor.register("pocdemo2","timeout",measureSet,dimensionSet, true);

        // 指定维度参数
        DimensionValueSet dimensionValues= DimensionValueSet.create();
        dimensionValues.setValue("result", isFailed ? "fail" : "success");
        dimensionValues.setValue("timeout", timeout ? "yes":"no");

        // 指定指标参数
        MeasureValueSet measureValues= MeasureValueSet.create();
        measureValues.setValue("deviceTime", deviceTime);
        measureValues.setValue("sendTime", sendTime);
        measureValues.setValue("backendTime", serverTime);
        measureValues.setValue("count", 1);

        //多维度多指标，最通用
        AppMonitor.Stat.commit("pocdemo2", "timeout", dimensionValues, measureValues);

    }


}
