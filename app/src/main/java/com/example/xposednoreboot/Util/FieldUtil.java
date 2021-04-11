package com.example.xposednoreboot.Util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具
 */
public class FieldUtil {
    public static final String varPliter = " , 变量名:";
    public static final String varHeader = "类型:";
    private static final String TAG = "FieldUtil";
    public static final String xposedClassKey = "__xposed__class__";
    public static final String xposedFilterHint = "__xposed__filter__";

    public static String hashmapToJson(Map<Object, Object> hashMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry entry : hashMap.entrySet()) {
            if (entry.getValue() instanceof Map) {
                sb.append("\"" + entry.getKey() + "\":" + hashmapToJson((Map<Object, Object>) entry.getValue()));
            } else {
                sb.append("\"" + entry.getKey() + "\":\"" + ((entry.getValue() != null && !entry.getKey().toString().startsWith("__xposed")) ? entry.getValue().getClass()+" ... " : "") + entry.getValue() + "\"");
            }
            sb.append(",");
        }
        sb.append("}");
        if (sb.charAt(sb.length() - 2) == ',') {
            sb.deleteCharAt(sb.length() - 2);
        }
        return sb.toString();
    }

    public static HashMap getVariableHashMapByDepth(Object o, Class tClass, Integer depth, boolean includeFunctions) {
        HashMap sb = new HashMap();
        if (depth == 0) {
            return getVariableDirectly(o, tClass);
        }
        if (includeFunctions)
            sb.putAll(getFunctionsMap(o));
        Field[] pickerFields = tClass.getDeclaredFields();
        for (Field pf : pickerFields) {
            try {
                pf.setAccessible(true);
                HashMap hashMap;
                String type = pf.getType().toString();
                if (isFiltered(pf)) {
//                    Log.d(TAG, "filtered " + pf.getType());
//                    hashMap = getVariableDirectly(o, tClass);
//                    hashMap.put(xposedClassKey, type);
//                    hashMap.put(xposedFilterHint, "filtered");
                    sb.put(pf.getName(), pf.get(o));
                } else {
//                    Log.d(TAG, "not filtered " + pf.getType());
                    hashMap = getVariableHashMapByDepth(pf.get(o), depth - 1, includeFunctions);
                    hashMap.put(xposedClassKey, type);
                    sb.put(pf.getName(), hashMap);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                sb.put(pf.getName(), "InaccessibleObjectException");
            }
        }
        return sb;
    }

    private static boolean isFiltered(Field pf) {
        return pf.getType().toString().equals("byte") ||
                pf.getType().toString().equals("short") ||
                pf.getType().toString().equals("int") ||
                pf.getType().toString().equals("long") ||
                pf.getType().toString().equals("float") ||
                pf.getType().toString().equals("double") ||
                pf.getType().toString().equals("char") ||
                pf.getType().toString().equals("boolean") ||
                pf.getType().toString().equals("class android.os.Handler") ||
                pf.getType().toString().equals("class java.lang.String") ||
                Regex.has(pf.getType().toString(), "class android.widget.*") ||
                Regex.has(pf.getType().toString(), "java.lang.*")||
                Regex.has(pf.getType().toString(), "android.support.v7.widget.*")
                ;
    }

    public static HashMap getVariableHashMapByDepth(Object o, boolean includeFunctions) {
        return getVariableHashMapByDepth(o, 0, false);
    }

    public static HashMap getVariableHashMapByDepth(Object o, Integer depth, boolean includeFunctions) {
        if (o == null) {
            return new HashMap();
        }
        return getVariableHashMapByDepth(o, o.getClass(), depth, includeFunctions);
    }

    public static HashMap getVariableDirectly(Object o, Class tClass) {
        HashMap sb = new HashMap();
        Field[] pickerFields = tClass.getDeclaredFields();
        for (Field pf : pickerFields) {
            try {
                pf.setAccessible(true);
                sb.put(pf.getName(), pf.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                sb.put(pf.getName(), "InaccessibleObjectException");
            }
        }
        return sb;
    }

    /**
     * @param tClass 手动设置class类
     */
    public static void set(Object object, Class tClass, String name, Object value) {
        try {
            Field pf = tClass.getDeclaredField(name);
            pf.setAccessible(true);
            try {
                pf.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置私有变量
     *
     * @param object 对象
     * @param name   变量名称
     * @param value  想要设定该变量的值
     */
    public static void set(Object object, String name, Object value) {
        set(object, object.getClass(), name, value);
    }

    /**
     * 获取对象的所有方法名称
     *
     * @param o 对象
     * @return 对象函数拼成的字符串
     */
    public static HashMap getFunctionsMap(Object o) {
        return getFunctionsMap(o, o.getClass());
    }

    public static HashMap getFunctionsMap(Object o, Class tClass) {
        HashMap sb = new HashMap();
        Method[] pickerFields = tClass.getDeclaredMethods();
        for (Method pf : pickerFields) {
            pf.setAccessible(true);
            sb.put(pf.getName(), pf);

        }
        return sb;
    }

    /**
     * 获取对象某个私有变量的值
     *
     * @param o    对象
     * @param name 变量名称
     * @return 获取的变量值
     */
    public static Object get(Object o, String name) {
        return get(o,o.getClass(), name);
    }

    public static Object get(Object o, Class tClass, String name) {
        try {
            Field pf = tClass.getDeclaredField(name);
            pf.setAccessible(true);
            try {
                return pf.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用对象的私有方法
     * <p>
     * !!!这是对于非重载的方法!!!
     * 也就是这个名字的函数只有一个的时候,因为反射依据就是这个methodName字符串
     * 详细原因见
     *
     * @param object     对象
     * @param methodName 方法名字
     * @param arguments  函数传参,第一层统一用new Object[]{arg1,arg2,arg3} 包裹，如果需要包含
     *                   Object ... objects 时，请用new Object[]{} 继续包裹
     *                   示例:
     *                   1.对于func(String s)，arguments应该是new Object[]{str}，之所以这么麻烦，
     *                   是为了解决3,4的问题
     *                   2.对于func(String s1,String s2)，arguments应该是new Object[]{str1,str2}
     *                   3.例如对于func(Object ... objects)，arguments应该是
     *                   new Object[]{new Object{arg1,arg2,arg3}}
     *                   4.如果是func(Object ... objects,Object ... objects2)，arguments应该是
     *                   new Object[]{new Object{arg1,arg2,arg3}，new Object{arg4,arg5,arg6}}
     *                   5.如果是空参数，arguments可以传null,也可以传new Object[]{}    (里面什么也没有)
     * @return 函数返回值，void和调用失败都返回null
     * @see FieldUtil#invoke(Object, String, Object[], Class[])
     * 的注释
     */
    public static Object invoke(Object object, String methodName, Object[] arguments) {
        return invoke(object, object.getClass(), methodName, arguments);
    }

    public static Object invoke(Object object, Class objectClass, String methodName, Object[] arguments) {
        Method[] methods = objectClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                try {
                    method.setAccessible(true);
                    return method.invoke(object, arguments);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 反射调用对象的私有方法
     *
     * @param object        对象
     * @param methodName    方法名称
     * @param arguments     第一层统一用new Object[]{arg1,arg2,arg3} 包裹，如果需要包含
     *                      Object ... objects 时，请用new Object[]{} 继续包裹
     * @param argumentTypes 参数类型，用new Class[] 包裹
     *                      加这个参数的原因，是函数可能会******重载******，
     *                      例如print(Object o)和print(String o)
     *                      如果传入一个"hello"对象，这两个函数都可以接收
     *                      在   for(Method method:methods)
     *                      （必须要遍历，因为object.getClass().getDeclaredMethod(methodName)只能得到public方法，失去了反射的意义）
     *                      的遍历中，是从上往下遍历的，先找到谁，如果能传入就直接调用了
     *                      所以加上一层参数判断  !Arrays.equals(method.getParameterTypes(),argumentTypes)
     *                      示例:
     *                      1.func(int a) new Class[]{int.class}
     *                      2.func(int ... a) new Class[]{int[].class}
     *                      3.func() new Class[]{}
     * @return 函数返回值，void和调用失败都返回null
     */
    public static Object invoke(Object object, String methodName, Object[] arguments, Class[] argumentTypes) {
        return invoke(object, object.getClass(), methodName, arguments, argumentTypes);
    }

    public static Object invoke(Object object, Class objectClass, String methodName, Object[] arguments, Class[] argumentTypes) {
        Method[] methods = objectClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                if (!Arrays.equals(method.getParameterTypes(), argumentTypes)) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    return method.invoke(object, arguments);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
