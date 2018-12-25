package com.taobao.demo.orange;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wuer on 2017/2/17.
 */

public class SPUtil {
    public static void putString(Context context, String spName, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String spName, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }
}
