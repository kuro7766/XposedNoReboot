package com.example.xposednoreboot.tests;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook_dao_meng_kong_jian {
    private static final String TAG = "Hook_dao_meng_kong_jian";

    public static void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if ("com.jingcai.apps.qualitydev".equals(loadPackageParam.packageName)) {
            XposedHelpers.findAndHookMethod(View.class, "setClickable", boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (param.thisObject instanceof TextView) {
                        if (((TextView) param.thisObject).getText().toString().equals("报名活动")) {
//                            ((TextView) param.thisObject).setClickable(true);
//                            ((TextView) param.thisObject).setFocusable(true);
                            ((TextView) param.thisObject).setTextColor(Color.RED);
                            param.args[0] = true;
//                            String s = (FieldUtil.getVariableHashMapByDepth(((TextView) param.thisObject).getContext()));
//                            Log.d(TAG, "beforeHookedMethod: "+(FieldUtil.getVariableDataString( ((TextView) param.thisObject).getContext())));
//                            Log.d(TAG, "beforeHookedMethod: "+param.args[0]);
//                            new FileOperator(FileOperator.ROOT + "xplog.txt").write(s);
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
