package com.taobao.demo.weex;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/4/23.
 * 去除BottomNavigationView的切换效果
 */

public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);

        try {

            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");

            shiftingMode.setAccessible(true);

            shiftingMode.setBoolean(menuView, false);

            shiftingMode.setAccessible(false);


            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);

                itemView.setShiftingMode(false);

                itemView.setChecked(itemView.getItemData().isChecked());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
