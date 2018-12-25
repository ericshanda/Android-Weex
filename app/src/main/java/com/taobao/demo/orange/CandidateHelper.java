package com.taobao.demo.orange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.taobao.orange.ICandidateCompare;
import com.taobao.orange.OCandidate;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.candidate.IntCompare;
import com.taobao.orange.candidate.StringCompare;
import com.taobao.orange.candidate.VersionCompare;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuer on 2018/5/25.
 */

public class CandidateHelper {
    private static DatabaseHelper dbHelper;

    public static Map<String, OCandidate> candidateMap = new HashMap<>();

    public static void init(Context context) {
        dbHelper = new DatabaseHelper(context, "orange.db", null, 1);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("candidate", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String key = cursor.getString(0);
                String clientValue = cursor.getString(1);
                String compareClz = cursor.getString(2);
                OCandidate candidate = getCandidate(key, clientValue, compareClz);
                if (candidate != null) {
                    candidateMap.put(key, candidate);
                }
                OrangeConfig.getInstance().addCandidate(candidate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    public static OCandidate getCandidate(String key, String clientValue, String compareClz) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(compareClz)) {
            return null;
        }
        Class<? extends ICandidateCompare> compare = StringCompare.class;
        if (StringCompare.class.getSimpleName().equals(compareClz)) {
            compare = StringCompare.class;
        } else if (IntCompare.class.getSimpleName().equals(compareClz)) {
            compare = IntCompare.class;
        } else if (VersionCompare.class.getSimpleName().equals(compareClz)) {
            compare = VersionCompare.class;
        }
        OCandidate candidate = new OCandidate(key, clientValue, compare);
        return candidate;
    }


    public static void addCandidate(OCandidate candidate) {
        if (candidate == null) {
            return;
        }
        if (candidateMap.get(candidate.getKey()) == null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("key", candidate.getKey());
            values.put("clientVal", candidate.getClientVal());

            String compare = candidate.getCompareClz();
            values.put("compareClz", compare.substring(candidate.getCompareClz().lastIndexOf(".") + 1));
            db.insert("candidate", null, values);
            db.close();
        } else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("clientVal", candidate.getClientVal());

            String compare = candidate.getCompareClz();
            values.put("compareClz", compare.substring(candidate.getCompareClz().lastIndexOf(".") + 1));
            db.update("candidate", values, "key = ?", new String[]{candidate.getKey()});
            db.close();
        }
        candidateMap.put(candidate.getKey(), candidate);
    }
}
