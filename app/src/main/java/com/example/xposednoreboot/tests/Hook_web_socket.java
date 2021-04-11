package com.example.xposednoreboot.tests;

import android.util.Log;

import java.net.Socket;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook_web_socket {
    private static final String TAG = "Hook_Hook_web_socket";

    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod(Socket.class, "getInputStream", null, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (param.thisObject instanceof Socket) {
                    Log.d(TAG, "beforeHookedMethod: " + param.thisObject.toString());
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });


    }
}
