package com.example.xposednoreboot;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.xposednoreboot.tests.Hook_dao_meng_kong_jian;
import com.example.xposednoreboot.tests.Hook_phone_invest;
import com.example.xposednoreboot.tests.Hook_test0;
import com.example.xposednoreboot.tests.Hook_web_socket;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author DX
 * 注意：该类不要自己写构造方法，否者可能会hook不成功
 * 开发Xposed模块完成以后，建议修改xposed_init文件，并将起指向这个类,以提升性能
 * 所以这个类需要implements IXposedHookLoadPackage,以防修改xposed_init文件后忘记
 * Created by DX on 2017/10/4.
 */

public class HookLogic implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private static final String TAG = "MyHookApp";
    private final static String modulePackageName = HookLogic.class.getPackage().getName();
    private XSharedPreferences sharedPreferences;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
//        Hook_dao_meng_kong_jian.handleLoadPackage(loadPackageParam);
//        Hook_web_socket.handleLoadPackage(loadPackageParam);
        Log.d(TAG, "handleLoadPackage: ");
//        Hook_test0.handleLoadPackage(loadPackageParam);
        Hook_phone_invest.handleLoadPackage(loadPackageParam);
//        Log.d(TAG, "handleLoadPackage: " + loadPackageParam.packageName);
//        if ("com.jingcai.apps.qualitydev".equals(loadPackageParam.packageName)) {
        if ("com.jingcai.apps.qualitydev".equals(loadPackageParam.packageName)) {
//            XposedHelpers.findAndHookConstructor(Activity.class,new Call)
//            XposedHelpers.findAndHookMethod(View.class, "setClickable", boolean.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    if (param.thisObject instanceof TextView) {
//                        if (((TextView) param.thisObject).getText().toString().equals("报名活动")) {
////                            ((TextView) param.thisObject).setClickable(true);
////                            ((TextView) param.thisObject).setFocusable(true);
//                            ((TextView) param.thisObject).setTextColor(Color.RED);
//
//                            param.args[0] = true;
////                            Log.d(TAG, "beforeHookedMethod: "+param.args[0]);
//
//                        }
//                        if (((TextView) param.thisObject).getText().toString().equals("已结束")) {
////                            ((TextView) param.thisObject).setClickable(true);
////                            ((TextView) param.thisObject).setFocusable(true);
//                            ((TextView) param.thisObject).setTextColor(Color.BLUE);
//                            ((TextView) param.thisObject).setText("报名活动");
//                            param.args[0] = true;
////                            Log.d(TAG, "beforeHookedMethod: "+param.args[0]);
//
//                        }
//
//
////                        Log.d(TAG, "beforeHookedMethod: "+((TextView) param.thisObject).getText().toString());
//                    }
////                    Log.d(TAG, "beforeHookedMethod: "+((TextView)param.thisObject).getContext().getClass());
//
////                    param.args[0] = "这是被Xposed Hook修改的文本";
////                    XposedBridge.log("Xposed 成功 Hook 目标方法");
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                }
//            });

//
//            XposedHelpers.findAndHookMethod(View.class, "setOnClickListener", View.OnClickListener.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    if (param.thisObject instanceof TextView) {
//                        if (((TextView) param.thisObject).getText().toString().equals("报名活动")) {
//                            ((TextView) param.thisObject).setClickable(true);
//                            ((TextView) param.thisObject).setFocusable(true);
//                            ((TextView) param.thisObject).setTextColor(Color.RED);
//
////                            Log.d(TAG, "beforeHookedMethod: "+param.args[0]);
//                        }
////                        Log.d(TAG, "beforeHookedMethod: "+((TextView) param.thisObject).getText().toString());
//                    }
////                    Log.d(TAG, "beforeHookedMethod: "+((TextView)param.thisObject).getContext().getClass());
//
////                    param.args[0] = "这是被Xposed Hook修改的文本";
////                    XposedBridge.log("Xposed 成功 Hook 目标方法");
//                }
//
//                @Override
//                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    if (param.thisObject instanceof TextView) {
//                        if (((TextView) param.thisObject).getText().toString().equals("报名活动")) {
//                            ((TextView) param.thisObject).setClickable(true);
//                            ((TextView) param.thisObject).setFocusable(true);
//                            ((TextView) param.thisObject).setTextColor(Color.RED);
//                            ((TextView) param.thisObject).postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ((TextView) param.thisObject).callOnClick();
//                                    Log.d(TAG, "afterHookedMethod: 报名了");
//                                }
//                            }, 1000);
//
////                            Log.d(TAG, "beforeHookedMethod: "+param.args[0]);
//                        }
////                        Log.d(TAG, "beforeHookedMethod: "+((TextView) param.thisObject).getText().toString());
//                    }
//                }
//            });
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) {
        this.sharedPreferences = new XSharedPreferences(modulePackageName, "default");
        XposedBridge.log(modulePackageName + " initZygote");
    }
}
