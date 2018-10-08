package com.ayvytr.commonlibrary.constant;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ayvytr.commonlibrary.Env;
import com.ayvytr.network.ApiClient;

/**
 * @author admin
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initArouter();
    }

    private void initArouter() {
        if(Env.isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
        ApiClient.getInstance().init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}