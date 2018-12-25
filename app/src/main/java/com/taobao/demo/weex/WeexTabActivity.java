package com.taobao.demo.weex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.luyujie.innovationcourse.R;
import com.emas.weex.bundle.WeexPageFragment;
import com.taobao.demo.Utils;

import java.util.ArrayList;
import java.util.List;

public class WeexTabActivity extends BaseWeexActivity {
    private static final String TAG = "WeexTabActivity";

    private static final int MAX_TABSIZE = 5;
    private static final int MIN_TABSIZE = 2;
    private List<WeexPageFragment> mFragments;
    private BottomNavigationView mBottomNavigationView;
    private List<WeexPageFragment> mTabList;
    private FragmentManager mFragmentManager;
    private ArrayList<String> mJsSources = new ArrayList<>();
    private int mTabNum = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weex_activity_main);
        setCustomActionBar();
        setActionBarTitle(R.string.weex_home1);
        initFragment();
        initBottomTab();
        hideBack();
        showScan();
    }

    private void initFragment() {
        initJsSource();
        mFragments = new ArrayList<WeexPageFragment>(mTabNum);
        for (int i = 0; i < mTabNum; i++) {
            String jsSource = mJsSources.get(i);
            WeexPageFragment fragment = createFragment(jsSource);
            mFragments.add(fragment);
        }
    }

    private void initJsSource() {
        mJsSources.add("index.js");
        mJsSources.add("filance.js");
        mJsSources.add("life.js");
        mJsSources.add("mine.js");
        mJsSources.add("mine.js");

        try {
            JSONObject jobj = Utils.getWeexContainerJobj(mContext);
            JSONArray jsSources = JSON.parseArray(jobj.getString("JsSource"));
            if (jsSources != null && jsSources.size() > 0) {
                mJsSources.clear();
                for (Object o : jsSources.toArray()) {
                    mJsSources.add((String)o);
                }
            }
            int tabSize = jobj.getIntValue("TabSize");
            if (tabSize > MAX_TABSIZE) {
                tabSize = MAX_TABSIZE;
            }

            if (tabSize < MIN_TABSIZE) {
                tabSize = MIN_TABSIZE;
            }
            mTabNum = tabSize;
        } catch (Throwable t) {
            Log.e(TAG, "prase weex-container");
        }
    }


    private void initBottomTab() {
        if (mTabList == null) {
            mTabList = new ArrayList<>();
            for (int i = 0; i < mFragments.size(); i++) {
                mTabList.add(mFragments.get(i));
            }
        }

        mFragmentManager = getSupportFragmentManager();
        switchFragment(0);

        mBottomNavigationView = findViewById(R.id.bottom_Navigation);
        //去除BottomNavigationView的切换效果
        mBottomNavigationView.setItemIconTintList(null);

        switch (mFragments.size()) {
            case 1:
                mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_1);
                break;
            case 2:
                mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_2);
                break;
            case 3:
                mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_3);
                break;
            case 4:
                mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_4);
                break;
            case 5:
                mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_5);
                break;
            default:
                mBottomNavigationView.inflateMenu(R.menu.bottom_navigation_4);
                break;
        }

        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottombar_item_home) {
                    switchFragment(0);
                    return true;
                }
                if (item.getItemId() == R.id.bottombar_item_filance) {
                    switchFragment(1);
                    return true;
                }
                if (item.getItemId() == R.id.bottombar_item_life) {
                    switchFragment(2);
                    return true;
                }
                if (item.getItemId() == R.id.bottombar_item_setting) {
                    switchFragment(3);
                    return true;
                }
                if (item.getItemId() == R.id.bottombar_item_other1) {
                    switchFragment(4);
                    return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < mTabList.size(); i++) {
            if (index == i) {
                transaction.show(mTabList.get(index));
            } else {
                transaction.hide(mTabList.get(i));
            }
        }
        transaction.commit();
    }
}
