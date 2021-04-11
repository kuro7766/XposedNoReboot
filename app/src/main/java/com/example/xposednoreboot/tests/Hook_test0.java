package com.example.xposednoreboot.tests;

import android.util.Log;
import android.widget.TextView;

import com.example.xposednoreboot.Util.FieldUtil;
import com.example.xposednoreboot.Util.FileOperator;

import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook_test0 {
    private static final String TAG = "Hook_test0";
    static boolean flag = false;
    public static final int unJoin = 2239488;

    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        //        Log.d(TAG, "handleLoadPackage: aaa");
//        Log.d(TAG, "handleLoadPackage: "+FieldUtil.hashmapToJson(FieldUtil.getVariableHashMapByDepth(loadPackageParam.appInfo)));
//        Log.d(TAG, "handleLoadPackage: "+loadPackageParam.appInfo+loadPackageParam.processName);
        if (loadPackageParam.packageName.equals("com.jingcai.apps.qualitydev")) {
            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

//                    if (param.thisObject instanceof TextView && !flag) {
//                        if (param.args[0].equals("报名活动")) {
//                            param.args[0] = "hook";
//                            Object a = ((TextView) param.thisObject).getContext();
//                            HashMap hashMap = FieldUtil.getVariableHashMapByDepth(a, 0, false);
////                        Log.d(TAG, "beforeHookedMethod: "+hashMap.toString());
//                            Log.d(TAG, "beforeHookedMethod: pre");
//                            Object currentEntity=FieldUtil.get(a,"currentEntity");
//                            Log.d(TAG, "beforeHookedMethod: "+currentEntity);
//                            FieldUtil.set(currentEntity,"activityId",unJoin);
//                            FieldUtil.set(a,"activityId",unJoin+"");
//                            Log.d(TAG, "beforeHookedMethod: activityid"+FieldUtil.get(a,"activityId"));
//                            Log.d(TAG, "beforeHookedMethod: "+FieldUtil.get(currentEntity,"activityId"));
//                            synchronized (TAG) {
//                                if (!flag) {
////                                    new FileOperator("xposedlogs.txt").write(FieldUtil.hashmapToJson(hashMap).replaceAll("\n", ""));
////                                    Log.d(TAG, "beforeHookedMethod: " + a.getClass());
//                                    flag = true;
//                                }
//                            }
//                        }
//                    }
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
        }
    }
}
