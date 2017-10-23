package com.kkbox.openapiclient;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injection.init(this);
    }

}
