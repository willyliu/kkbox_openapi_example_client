package com.kkbox.openapiclient;

import android.content.Context;

public class Injection {

    private static Context context;

    public static void init(Context context) {
        Injection.context = context;
    }

    public static OpenApiWrapper provideOpenApiWrapper() {
        return new OpenApiWrapper(context);
    }

}
