package com.taobao.demo;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
/**
 * Sophix入口类，专门用于初始化Sophix，不应包含任何业务逻辑。
 * 此类必须继承自SophixApplication，onCreate方法不需要实现。
 * 此类不应与项目中的其他类有任何互相调用的逻辑，必须完全做到隔离。
 * AndroidManifest中设置application为此类，而SophixEntry中设为原先Application类。
 * 注意原先Application里不需要再重复初始化Sophix，并且需要避免混淆原先Application类。
 * 如有其它自定义改造，请咨询官方后妥善处理。
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(DemoApplication.class)
    static class RealApplicationStub {}
    
    
    //添加拉取Patch包方法
    @Override
    public void onCreate() {
        super.onCreate();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        Utils.initConfig(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setHost(EmasInit.getInstance().mMTOPDoman, false)
                .setSecretMetaData(EmasInit.getInstance().mAppkey, EmasInit.getInstance().mAppSecret, "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCAmdpFnkrvjDCnZwhbWx4xbiRQtMWLZs/X4879JVP/j5T18wwjiVJVOURiWMdqrxC/ibqfLUX8GypEbx/d+F83H+xpNsaVGdxzqZQJdioywhWMOmHiNGbNBdTPDR3wBbNc9YOb+zSd2GqvvBfIefH+zG3SY8UJRZfganyRkEz892tf7am5Syq2c/KB+BlGvU6gpIaNRrlNhr7QfvJiH1lfVIJTKelq2N/EVmMUiGgUMhbjVN5GO04mt9zIVvUHSLl07Oi+bYgAFgC+gNCPJFX4MRqhdJdL79Jz51PTnStxDarZdhDPqr2PGxUGTVzpx/kSv0YjdrVvWfvxp+DL1O4NAgMBAAECggEAC1qJ9L+G6iM8YGsuR7KBted9eUqcbjRDgVHxglP7WIK6qgQtPC3xgOivaudfo8GU/vQP7+G1Xg6f6q7M53H4JgvBeYCGg79mwSdllHhEPXr5IAo/fhMWSF+NbEJCNGCsdzaNygWhnZXvxVgb2W06uj4eLAb27FyijgOYv9k3ktW85cexA9CxBkBYMy8W+q9kRFZUY7odVTurlFp0+Le3E9cs1fvmfZ/2j5uAM5PrKvT/4ADx7i21kBxKLliOBOTm7Jd3dHEuoUm5C/6UavV+M+ue2kyx/XZYrqsjSMDHgpm2K+agQwVkG2qUPdLs6ylMTITK9Or6p1sp15zZHOYQmQKBgQDPydKgWnBoatejoDNGSnhwswkbTXzGXP6rMkKfgm+R/Q50fCaQtdEfkjjLFIMdwDdWRS8OyRhmpQkSm2rwiN5Pd+eZqVuB2U4X4NxDqehJ8rLlkFSGYQ6cFsH9XZIQIgiDZLR55vOrOcUhi4MbowBkQ80nxldMo5YdqI1ckfm3OwKBgQCecHi2NlbfXd4JLE7y8KkVVaTxktuwP1TOJyCRYkRF8YbjDFbysybkDZEUuPVUSeJhiSOwjmhhRqq00kaz/idY2APNcwSdW/KL9uD4bjheE79jyvkeLxcYliQZ2cSoTMD7q8Lo3atXUcQMD9fZeXl4DrLIirKWFnVTO/oCPwZrVwKBgQCvPh+Nbbjv12wWa73SwmvhYJ2FkUxyX2vJJpJZxMAj97CBPVOpKC4+2itb2ujZ5orFD4QojCEwMG/+aWU/h682hoGlbtfmI0dhjBs4Vcu60h1ezuL6klCoA+mHrarKQGm9pukvBkDrX8WKDQd9/3MNHMGhkf1+fVjVmpneQJ7CkQKBgQCW1M+192kgX8YeyXWnRYBR9SR/baWC1eewEgOpI/mG9HXK9FZZ2j9t35R8O9Ahm+zNmbbGP+wSnymn1n7En3Fp3MtR7Os5uuzeXfqxxowW04niQLqEB6RfHZ/klDHpXphhfpShB1Y0XHzcVsDQIhqHNsbwr0lwJJUw1eod9U3xJQKBgCdVX67HqkKw0zr6Weqnci/YIPibI/8r/67Zy2C3+uIGkvrKrFGNNtl8Tlf3QXNI8BGBhjzBmd9C2R8I7+5j3/SBQflTczw/kDNu50hIC9Rx5DTTqC4MxHir/rB3I0LbwhrbRrhk8uih9a4JqShgnSXisjvLIFfcuTkuQiw0TJqa")
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }
}