package com.taobao.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.emas.man.EMASMANService;
import com.alibaba.android.emas.man.EMASMANServiceFactory;
import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.ha.adapter.AliHaConfig;
import com.alibaba.ha.adapter.Plugin;
import com.alibaba.ha.adapter.Sampling;
import com.example.luyujie.innovationcourse.BuildConfig;
import com.emas.weex.EmasWeex;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsException;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.IRegister;
import com.taobao.agoo.TaobaoRegister;
import com.taobao.demo.agoo.AgooActivity;
import com.taobao.demo.orange.BaseBFgroundSwitch;
import com.taobao.demo.testing.weex.TestHaModule;
import com.taobao.demo.weex.WeexCrashListener;
import com.taobao.demo.weex.module.EmasNavigatorModule;
import com.taobao.orange.OConfig;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.impl.LogcatLog;
import com.taobao.orange.util.OLog;
import com.taobao.update.adapter.UpdateAdapter;
import com.taobao.update.apk.ApkUpdater;
import com.taobao.update.common.Config;
import com.taobao.update.common.framework.UpdateRuntime;
import com.taobao.update.datasource.UpdateDataSource;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.example.luyujie.innovationcourse.BuildConfig;


import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.android.spdy.SpdyProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anet.channel.SessionCenter;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.IStrategyInstance;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.http.NetworkSdkSetting;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.global.SwitchConfig;
import mtopsdk.mtop.intf.Mtop;
import mtopsdk.mtop.intf.MtopEnablePropertyType;
import mtopsdk.mtop.intf.MtopSetting;
import mtopsdk.security.LocalInnerSignImpl;

/**
 * Created by jason on 18/1/15.
 */

public class EmasInit {
    public static final int DEBUG = 1;    //测试环境
    public static final int RELEASE = 2;  //发布环境

    private static final String XIAOMI_APPID = "2882303761517899829";//"2882303761517844958";
    private static final String XIAOMI_APPKEY = "5931789941829";//"5171784438958";

    private static final String OPPO_APPKEY = "";
    private static final String OPPO_APPSECRET = "";

    private static final String MEIZU_APPID = "117094";//"114980";
    private static final String MEIZU_APPKEY = "90131236323e47d8b9435d022cba57bc";//"15f582f2fc67440ebcbc98c76fee2d26";

    /*配置信息*/
    protected String mAppkey = "10000021";//"10000066";//"10000039";//"10000078";//"60039748";
    protected String mAppSecret = "f7bd9a1adabacebfc1edf933ff1c020d";//"1426c10c5ce57d6cb29e016a816421a7";//"c7795717b2306055f21fb33418c1d011";//"2e00a7e9ab2048daabd4977170d37c4a";//"ab5ff148782b467bb0b310c4acd70abd"//"fe240d4b8f4b31283863cc9d707e2cb1"
    protected String mCacheURL = "http://cdn.emas-poc.com/eweex/";
    protected String mACCSDoman = "accs.emas-poc.com";
    protected Map<String, String> mIPStrategy;
    public String mMTOPDoman = "aserver.emas-poc.com";
    protected String mHAUniversalHost = "adash.emas-poc.com";
    protected String mHAOSSBucketName = "emas-ha-remote-log-poc";
    protected String mHARSAPublicKey;
    protected String mStartActivity = "com.taobao.demo.NativeDemoActivity";
    protected String mChannelID = "1001@DemoApp_Android_" + BuildConfig.VERSION_NAME;
    protected String PUSH_TAG = "POC";
    protected boolean mUseHttp = true;
    protected String mReceivePushService = "com.taobao.demo.agoo.DemoAgooService";

    protected int mEnv = DEBUG;

    private Application mApplication;
    private static final String TAG = "EmasInit";

    public final static String SERVICE_ID = "4272_mock";
    //private static final String[] TEST_EMAS = new String[]{"acs.alibaba-inc.com", "11.163.130.35", "80"};


    private final static Map<String, String> SERVICES = new HashMap<String, String>() {
        private static final long serialVersionUID = 2527336442338823324L;

        {
            //自定义服务, 找@子琛 注册
            put(SERVICE_ID, "com.taobao.demo.accs.TestAccsService");
        }
    };

    /**
     * 切换成单例
     */
    private static class CreateInstance {
        private static EmasInit instance = new EmasInit();
    }

    public static EmasInit getInstance() {
        return CreateInstance.instance;
    }

    private EmasInit() {
        //不能生成对象
    }

    //先设置mApplication
    public EmasInit setmApplication(Application application) {
        this.mApplication = application;
        //初始化
        firstInit();
        return this;
    }

    private void firstInit() {
        Application application = mApplication;
        StringBuilder builder = new StringBuilder();
        try {
            int id = application.getResources().getIdentifier("ttid", "string", application.getPackageName());
            if (id > 0) {
                mChannelID = builder.append(application.getString(id))
                    .append("@")
                    .append(application.getResources().getString(application.getApplicationInfo().labelRes))
                    .append("_")
                    .append("Android")
                    .append("_")
                    .append(BuildConfig.VERSION_NAME).toString();
            }
        } catch (Resources.NotFoundException e) {
            Log.d(TAG, "no channel id in res" + e.toString());
        }
    }

    /********************UPDATE SDK START **************************/
    public void initUpdate() {
        initMtop();
        Config config = new Config();
        config.group = mAppkey + "@android";//AppInfoHelper.getGroup();
        config.ttid = mChannelID;
        config.isOutApk = false;
        config.appName = "EMAS Demo";
        ApkUpdater apkupdate = new ApkUpdater();
        UpdateAdapter updateAdapter = new UpdateAdapter();
        UpdateDataSource.getInstance().init(mApplication, config.group, config.ttid, config.isOutApk, updateAdapter);
        UpdateRuntime.init(mApplication, config.ttid, config.appName, config.group);
        UpdateDataSource.getInstance().startUpdate(false, false);
    }

    private void initMtop() {

        if (mEnv == DEBUG) {
            TBSdkLog.setTLogEnabled(false);
            TBSdkLog.setLogEnable(TBSdkLog.LogEnable.DebugEnable);
        }

        //关闭密文
        if (mUseHttp) {
            NetworkConfigCenter.setSSLEnabled(false);
        }

        //[option]关闭MTOP请求长链,调用后Mtop请求直接调用NetworkSDK的HttpNetwork发请求
        SwitchConfig.getInstance().setGlobalSpdySwitchOpen(false);

        //关闭MTOPSDK NewDeviceID逻辑
        MtopSetting.setEnableProperty(Mtop.Id.INNER, MtopEnablePropertyType.ENABLE_NEW_DEVICE_ID, false);

        //设置自定义全局访问域名
        MtopSetting.setMtopDomain(Mtop.Id.INNER, mMTOPDoman, mMTOPDoman, mMTOPDoman);

        //设置自定义签名使用的appKey和appSecret
        MtopSetting.setISignImpl(Mtop.Id.INNER, new LocalInnerSignImpl(mAppkey, mAppSecret));

        MtopSetting.setAppVersion(Mtop.Id.INNER, BuildConfig.VERSION_NAME);

        Mtop mtopInstance = Mtop.instance(Mtop.Id.INNER, mApplication.getApplicationContext(), mChannelID);

    }
    /********************UPDATE SDK END **************************/


    /********************WEEX SDK START **************************/
    public void initWeex() {
        try {
            ImagePipelineConfig imageConfig = ImagePipelineConfig.newBuilder(mApplication)
                    .setDownsampleEnabled(true)
                    .build();
            Fresco.initialize(mApplication, imageConfig);

            EmasWeex.Config econfig = new EmasWeex.Config.Builder()
                    .setAppkey(mAppkey)
                    .setAppVersion(BuildConfig.VERSION_NAME)
                    .setZcacheUrl(mCacheURL).build();
            EmasWeex.getInstance().init(mApplication, econfig);
            WXSDKEngine.registerModule("navigator", EmasNavigatorModule.class);
            WXSDKEngine.registerModule("haTest", TestHaModule.class);
        } catch (WXException var6) {
            var6.printStackTrace();
        }
    }
    /********************WEEX SDK END **************************/


    /********************HA SDK START **************************/
    public void initHA() {
        //开启
        if (mEnv == DEBUG) {
            AliHaAdapter.getInstance().openDebug(true);
        }
        AliHaAdapter.getInstance().changeHost(mHAUniversalHost);
        AliHaAdapter.getInstance().tLogService.changeRemoteDebugHost(mHAUniversalHost);
        AliHaAdapter.getInstance().tLogService.changeRemoteDebugOssBucket(mHAOSSBucketName);
        if (!TextUtils.isEmpty(mHARSAPublicKey)) {
            AliHaAdapter.getInstance().tLogService.changeRasPublishKey(mHARSAPublicKey);
        }
        initHACrashreporterAndUt();
        AliHaAdapter.getInstance().openHttp(mUseHttp);

        AliHaAdapter.getInstance().removePugin(Plugin.crashreporter); //tlog 依赖accs
        AliHaAdapter.getInstance().removePugin(Plugin.ut);

        AliHaAdapter.getInstance().telescopeService.setBootPath(new String[]{mStartActivity}, System.currentTimeMillis());
        AliHaAdapter.getInstance().start(buildAliHaConfig());

        AliHaAdapter.getInstance().crashService.addJavaCrashListener(new WeexCrashListener());
    }


    private AliHaConfig buildAliHaConfig() {
        //ha初始化
        AliHaConfig config = new AliHaConfig();
        config.isAliyunos = false;
        config.appKey = mAppkey;
        config.userNick = "you need set user name";
        config.channel = mChannelID;
        config.appVersion = BuildConfig.VERSION_NAME;
        config.application = mApplication;
        config.context = mApplication;

        return config;
    }

    private void initHACrashreporterAndUt() {
        AliHaAdapter.getInstance().startWithPlugin(buildAliHaConfig(), Plugin.crashreporter);
        AliHaAdapter.getInstance().startWithPlugin(buildAliHaConfig(), Plugin.ut);
        AliHaAdapter.getInstance().utAppMonitor.changeSampling(Sampling.All);
    }


    //通道服务
    public void initAccs() {
        if (mIPStrategy != null && mIPStrategy.size() > 0) {
            initNetwork();
        }

        boolean isDebug = mEnv == DEBUG || ((mApplication.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        if (isDebug) { //debug版本, 打开日志开关, 方便排查问题
            ALog.setUseTlog(false);
            anet.channel.util.ALog.setUseTlog(false);
        }

        new Thread() { //建议异步进行初始化
            @Override
            public void run() {
                int env = Constants.RELEASE;
                int pubkey = SpdyProtocol.PUBKEY_PSEQ_EMAS;//SpdyProtocol.PUBKEY_SEQ_TEST
                String appkey = mAppkey;//"4272"
                String appsecret = mAppSecret;//"257461a8005f538382640d4894dd193a04d18e1b4a7a5ee214b6d660778d3943"
                String emasHost = mACCSDoman;
                SharedPreferences sp = mApplication.getSharedPreferences("emas_accs", Context.MODE_PRIVATE);
                String key  = sp.getString("appkey", null);
                String secret = sp.getString("appsecret", null);
                appkey = TextUtils.isEmpty(key) ? appkey : key;
                appsecret = TextUtils.isEmpty(secret) ? appsecret : secret;

                try {
                    ACCSManager.setAppkey(mApplication.getApplicationContext(), mAppkey, env);//兼容老接口 如果有任意地方使用老接口，必须setAppkey
                    NetworkSdkSetting.init(mApplication.getApplicationContext());
                    //关闭AMDC请求
                    HttpDispatcher.getInstance().setEnable(false);
                    ACCSClient.setEnvironment(mApplication.getApplicationContext(), env);
                    AccsClientConfig clientConfig = new AccsClientConfig.Builder()
                            .setAppKey(appkey)
                            .setAppSecret(appsecret)
                            .setInappHost(emasHost)
                            .setInappPubKey(pubkey)
                            .setChannelHost(emasHost)
                            .setChannelPubKey(pubkey)
                            .setTag(AccsClientConfig.DEFAULT_CONFIGTAG)
                            .setConfigEnv(env)
                            .build();
                    ACCSClient.init(mApplication, clientConfig);
                    ACCSClient.getAccsClient(AccsClientConfig.DEFAULT_CONFIGTAG).bindApp(mChannelID, mAppReceiver);
                    initAgooPush();
                } catch (AccsException e) {
                    ALog.w(TAG, "initDefaultAccs", e);
                }
            }
        }.start();
    }

    //推送服务
    private void initAgooPush() {

        try {
            TaobaoRegister.setAgooMsgReceiveService(mReceivePushService);
            TaobaoRegister.register(mApplication, AccsClientConfig.DEFAULT_CONFIGTAG, mAppkey, mAppSecret, "",
                    new IRegister() {
                        @Override
                        public void onSuccess(String deviceToken) {
                            ALog.d(TAG, "agoo register success");
                            AgooActivity.accsState = "online";
                            AgooActivity.accsToken = deviceToken;
                        }

                        @Override
                        public void onFailure(String s, String s1) {
                            ALog.w(TAG, "agoo register fail" + s + s1);
                            AgooActivity.accsState = "error:" + s;
                        }
                    });
            //三方通道初始化, 在各家push官网申请拿到, 同时服务token务必填入EMAS推送平台
            String phoneBrand = Build.BRAND;
            ALog.d(TAG, "initAgoo start", "phoneBrand", phoneBrand);
            //简易判断机型, 可能不标准, 仅供参考
            if ("huawei".equalsIgnoreCase(phoneBrand) || "honor".equalsIgnoreCase(phoneBrand)) {
                HuaWeiRegister.register(mApplication);//华为通道（可选）
            } else if ("xiaomi".equalsIgnoreCase(phoneBrand)) {
                MiPushRegistar.register(mApplication, XIAOMI_APPID, XIAOMI_APPKEY);//小米通道（可选）
            } else if ("oppo".equalsIgnoreCase(phoneBrand)) {
                OppoRegister.register(mApplication, OPPO_APPKEY, OPPO_APPSECRET);//OPPO通道（可选）
            } else if ("meizu".equalsIgnoreCase(phoneBrand)) {
                MeizuRegister.register(mApplication, MEIZU_APPID, MEIZU_APPKEY);//MEIZU通道(可选）
            }
            //GcmRegister.register(mApplication, GCM_SEND_ID, GCM_APPLICATION_ID); //GCM通道（可选）
        } catch (AccsException e) {
            e.printStackTrace();
        }
    }

    private IAppReceiver mAppReceiver = new IAppReceiver() {
        private String TAG = "mAppReceiver";

        @Override
        public void onBindApp(int errorCode) {
            ALog.i(TAG, "onBindApp", "errorCode", errorCode);
            try {
                ACCSClient.getAccsClient(AccsClientConfig.DEFAULT_CONFIGTAG).bindUser("123324234");
            } catch (AccsException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUnbindApp(int errorCode) {
            ALog.i(TAG, "onUnbindApp", "errorCode", errorCode);
        }

        @Override
        public void onBindUser(String userId, int errorCode) {
            ALog.i(TAG, "onBindUser", "errorCode", errorCode);
        }

        @Override
        public void onUnbindUser(int errorCode) {
            ALog.i(TAG, "onUnbindUser", "errorCode", errorCode);
        }

        @Override
        public void onSendData(String dataId, int errorCode) {
            ALog.i(TAG, "onSendData");
        }

        @Override
        public void onData(String userId, String dataId, byte[] data) {
            ALog.i(TAG, "onData");
        }

        @Override
        public String getService(String serviceId) {
            String service = SERVICES.get(serviceId);
            return service;
        }

        @Override
        public Map<String, String> getAllServices() {
            return SERVICES;
        }
    };

    private void initNetwork() {
        SessionCenter.init(mApplication);
        final IStrategyInstance instance = StrategyCenter.getInstance();
        StrategyCenter.setInstance(new IStrategyInstance() {
            @Override
            public void initialize(Context context) {
                instance.initialize(context);
            }

            @Override
            public void switchEnv() {
                instance.switchEnv();
            }

            @Override
            public void saveData() {
                instance.saveData();
            }

            @Override
            public String getFormalizeUrl(String rawUrlString) {
                return instance.getFormalizeUrl(rawUrlString);
            }

            @Override
            public List<IConnStrategy> getConnStrategyListByHost(String host) {
                String strategy = mIPStrategy.get(host);
                if (TextUtils.isEmpty(strategy)) {
                    return instance.getConnStrategyListByHost(host);
                }
                final String[] ipPort = strategy.split(":");
                List<IConnStrategy> list = new ArrayList<IConnStrategy>();

                IConnStrategy connStrategy = new IConnStrategy() {
                    @Override
                    public String getIp() {
                        return ipPort[0];
                    }

                    @Override
                    public int getIpType() {
                        return 0;
                    }

                    @Override
                    public int getIpSource() {
                        return 0;
                    }

                    @Override
                    public int getPort() {
                        return Integer.parseInt(ipPort[1]);
                    }

                    @Override
                    public ConnProtocol getProtocol() {
                        return ConnProtocol.valueOf("http2", "0rtt", "emas", false);
                    }

                    @Override
                    public int getConnectionTimeout() {
                        return 10000;
                    }

                    @Override
                    public int getReadTimeout() {
                        return 10000;
                    }

                    @Override
                    public int getRetryTimes() {
                        return 1;
                    }

                    @Override
                    public int getHeartbeat() {
                        return 0;
                    }
                };
                list.add(connStrategy);
                return list;
            }

            @Override
            public String getSchemeByHost(String host) {
                return instance.getSchemeByHost(host);
            }

            @Override
            public String getSchemeByHost(String host, String dftScheme) {
                return instance.getSchemeByHost(host, dftScheme);
            }

            @Override
            public String getCNameByHost(String host) {
                return instance.getCNameByHost(host);
            }

            @Override
            public String getClientIp() {
                return instance.getClientIp();
            }

            @Override
            public void notifyConnEvent(String host, IConnStrategy connStrategy, ConnEvent connEvent) {
                instance.notifyConnEvent(host, connStrategy, connEvent);
            }

            @Override
            public String getUnitByHost(String s) {
                return null;
            }

            @Override
            public void forceRefreshStrategy(String host) {
                instance.forceRefreshStrategy(host);
            }

            @Override
            public void registerListener(IStrategyListener listener) {
                instance.registerListener(listener);
            }

            @Override
            public void unregisterListener(IStrategyListener listener) {
                instance.unregisterListener(listener);
            }
        });
    }

    /********************HA SDK END **************************/

    public void initConfig(Application application) {
        boolean isDebug = ((application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        if (isDebug) {
            OLog.setInstance(new LogcatLog() {
                @Override
                public OLog.Level getLevel() {
                    return OLog.Level.V;
                }
            });
        }
        //前后台切换监听
        BaseBFgroundSwitch.init(application).setOnTaskSwitchListener(new BaseBFgroundSwitch.OnTaskSwitchListener() {
            @Override
            public void onTaskSwitchToForeground() {
                Log.d(TAG, "切换到前台");
                OrangeConfig.getInstance().forceCheckUpdate();
            }

            @Override
            public void onTaskSwitchToBackground() {
                Log.d(TAG, "切换到后台");
            }
        });

        //CandidateHelper.init(application);
        String appVersion = "1.0.0";
        try {
            appVersion = application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }
        /*appKey = mAppkey;
        appSecret = mAppSecret;*/

        OConfig config = new OConfig.Builder()
            .setAppKey(mAppkey)
            .setAppSecret(mAppSecret)
            .setAppVersion(appVersion)
            .setDcHost(mMTOPDoman)
            .build();
        OrangeConfig.getInstance().init(application, config);
    }

    public void initMAN(Application application) {
        EMASMANService manService = EMASMANServiceFactory.getMANService();
        manService.init(application, mAppkey, mAppSecret);
    }

    public String getAppkey() {
        return mAppkey;
    }

    public String getAppSecret() {
        return mAppSecret;
    }

    public String getACCSDoman() {
        return mACCSDoman;
    }

    public Map<String, String> getIPStrategy() {
        return mIPStrategy;
    }
}
