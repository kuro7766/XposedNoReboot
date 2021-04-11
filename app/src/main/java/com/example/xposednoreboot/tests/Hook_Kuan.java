package com.example.xposednoreboot.tests;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.example.xposednoreboot.Util.FieldUtil;
import com.example.xposednoreboot.Util.FileOperator;

import java.util.Arrays;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook_Kuan {
    private static final String TAG = "Hook_Kuan";
    private static volatile boolean run = false;

    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
//        if (loadPackageParam.packageName.equals("com.coolapk.market")) {
        if (true) {
            Log.d(TAG, "handleLoadPackage: onload");
            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    Log.d(TAG, "beforeHookedMethod: " + param.args[0]);
//                    Log.d(TAG, "beforeHookedMethod() called with: param = [" + param + "]");
//                    Log.d(TAG, "beforeHookedMethod() called with: param = [" + param + "]");
//                    Log.d(TAG, "beforeHookedMethod: " + Arrays.toString(param.args));
                    if (param.thisObject instanceof TextView && ((TextView) param.thisObject).getContext() instanceof Activity) {
                        Log.d(TAG, "beforeHookedMethod: " + ((TextView) param.thisObject).getContext());

//                        if (run) {
//                            return;
//                        }
//                        run = true;

                        Object activity = ((TextView) param.thisObject).getContext();
                        HashMap h = FieldUtil.getVariableHashMapByDepth(activity, 4, false);
                        String s = FieldUtil.hashMapToJson(h);
                        Log.d(TAG, s);
                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
        }
    }

}
