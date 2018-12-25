package com.taobao.demo.testing.weex;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bliss on 18/1/4.
 */
public class TestHaModule extends WXModule {

    public TestHaModule() {
    }

    @JSMethod
    public void makeNpe(JSCallback callback) {
        String test = null;
        test.substring(1);
    }

    @JSMethod(uiThread = true)
    public void makeAnr(JSCallback callback) {
        String url = "https://www.baidu.com/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
