package com.taobao.demo.orange;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by wuer on 2017/2/15.
 */

public class BaseBFgroundSwitch implements Application.ActivityLifecycleCallbacks {
    public int mCount = 0;
    private OnTaskSwitchListener mOnTaskSwitchListener;
    private static BaseBFgroundSwitch mBaseLifecycle;

    public static BaseBFgroundSwitch init(Application application) {
        if (null == mBaseLifecycle) {
            mBaseLifecycle = new BaseBFgroundSwitch();
            application.registerActivityLifecycleCallbacks(mBaseLifecycle);
        }
        return mBaseLifecycle;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        if (mCount++ == 0) {
            mOnTaskSwitchListener.onTaskSwitchToForeground();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {
        if (--mCount == 0) {
            mOnTaskSwitchListener.onTaskSwitchToBackground();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}

    public void setOnTaskSwitchListener(OnTaskSwitchListener listener) {
        mOnTaskSwitchListener = listener;
    }

    public interface OnTaskSwitchListener {

        void onTaskSwitchToForeground();

        void onTaskSwitchToBackground();
    }
}
