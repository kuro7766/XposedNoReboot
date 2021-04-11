package com.example.xposednoreboot.tests;

import android.util.Log;
import android.widget.TextView;

import com.example.xposednoreboot.Util.FieldUtil;
import com.example.xposednoreboot.Util.FileOperator;

import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook_phone_invest {
    private static final String TAG = "Hook_phone_invest";
    static boolean flag = false;
    public static final int unJoin = 2239488;
    static boolean hasHooked = false;
    static int counter = 0;

    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        //        Log.d(TAG, "handleLoadPackage: aaa");
//        Log.d(TAG, "handleLoadPackage: "+FieldUtil.hashmapToJson(FieldUtil.getVariableHashMapByDepth(loadPackageParam.appInfo)));
//        Log.d(TAG, "handleLoadPackage: "+loadPackageParam.appInfo+loadPackageParam.processName);
        if (loadPackageParam.packageName.equals("com.cmbchina.ccd.pluto.cmbActivity")) {
            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    Log.d(TAG, "beforeHookedMethod: " + param.args[0]);
                    if (param.thisObject instanceof TextView && !flag) {
                        if (!hasHooked) {
                            if (param.args[0].equals("订单详情页")) {
                                param.args[0] = "hook" + counter;
                                counter++;
                                Object activity = ((TextView) param.thisObject).getContext();
                                Object webView = FieldUtil.get(activity, "webView");
                                HashMap hashMap = FieldUtil.getVariableHashMapByDepth(activity, 3, true);
//                        Log.d(TAG, "beforeHookedMethod: "+hashMap.toString());
                                Log.d(TAG, "beforeHookedMethod: pre");
                                new FileOperator("/sdcard/hookv.txt").write(FieldUtil.hashmapToJson(hashMap));
//                                Object currentEntity = FieldUtil.get(a, "currentEntity");
//                                Log.d(TAG, "beforeHookedMethod: " + currentEntity);
//                                FieldUtil.set(currentEntity, "activityId", unJoin);
//                                FieldUtil.set(a, "activityId", unJoin + "");
                            }
                        }

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
