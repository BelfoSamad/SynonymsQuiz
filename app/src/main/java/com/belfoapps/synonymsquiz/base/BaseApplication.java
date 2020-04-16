package com.belfoapps.synonymsquiz.base;

import com.onesignal.OneSignal;
import androidx.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        //Init One Signal
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
