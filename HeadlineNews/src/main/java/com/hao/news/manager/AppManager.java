package com.hao.news.manager;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

/**
 * Project_Name:AppNews
 * Author:朱永豪
 * Email:13838584575@163.com
 * on 2016/11/10.
 */

public class AppManager extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Logger.init("debugzhu");
    }
}
