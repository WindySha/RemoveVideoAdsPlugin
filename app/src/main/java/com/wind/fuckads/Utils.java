package com.wind.fuckads;

import android.content.Context;
import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static void xposedLog(String tag, XC_MethodHook.MethodHookParam param, boolean stackTrace) {
        StringBuilder build = new StringBuilder();
        if (param.args != null && param.args.length > 0) {
            int index = 0;
            for (Object arg : param.args) {
                build.append("   arg[" + index + "] = " + arg);
                index++;
            }
        }
        if (stackTrace) {
            Log.e(TAG, tag + "  this = " + param.thisObject + " \n " + build.toString() + " result = " + param.getResult(),
                    new Exception("showLogTrace -- method = " + param.method.getName()));
        } else {
            Log.e(TAG, tag + "  this = " + param.thisObject + " method = " + param.method.getName() +
                    " \n " + build.toString() + " result = " + param.getResult());
        }
    }

    public static String readTextFromAssets(Context context, String assetsFileName) {
        if (context == null) {
            return null;
        }
        try {
            InputStream is = context.getAssets().open(assetsFileName);
            return readTextFromInputStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readTextFromInputStream(InputStream is) {
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            reader = new InputStreamReader(is, "UTF-8");
            bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                builder.append(str);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSafely(reader);
            closeSafely(bufferedReader);
        }
        return null;
    }

    private static void closeSafely(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readAttributeValue(Object obj) {
        String nameVlues = "";
        //得到class
        Class cls = obj.getClass();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {//遍历
            try {
                //得到属性
                Field field = fields[i];
                //打开私有访问
                field.setAccessible(true);
                //获取属性
                String name = field.getName();
                //获取属性值
                Object value = field.get(obj);
                //一个个赋值
                nameVlues += name + "  -->  " + value + " \n ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int lastIndex = nameVlues.lastIndexOf("\n");
        //不要最后一个逗号","
        String result = nameVlues.substring(0, lastIndex);
        return result;
    }

    public static String readAttributeValue(Class cls, Object obj) {
        String nameVlues = "";
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {//遍历
            try {
                //得到属性
                Field field = fields[i];
                //打开私有访问
                field.setAccessible(true);
                //获取属性
                String name = field.getName();
                //获取属性值
                Object value = field.get(obj);
                //一个个赋值
                nameVlues += name + "  -->  " + value + " \n ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int lastIndex = nameVlues.lastIndexOf("\n");
        //不要最后一个逗号","
        String result = nameVlues.substring(0, lastIndex);
        return result;
    }

    public static String readAllFieldValue(Object obj) {
        Class cls = obj.getClass();
        String value = "";
        while (cls != null && !cls.isAssignableFrom(Object.class)) {
            value += readAttributeValue(cls, obj);
            cls = cls.getSuperclass();
        }
        return value;
    }
}
