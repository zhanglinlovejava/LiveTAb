package com.colin.livetvtab;

import android.app.Application;
import android.content.Context;

/**
 * Created by Colin.Zhang on 2017/1/9.
 */

public class TestApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

    public static Context getmContext() {
        return mContext;
    }

}
