package com.wind.fuckads.hook

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Wind
 */
class TencentVideoHook : IXposedHookLoadPackage {

    private val TENCENT_VIDEO_PACKAGE_NAME = "com.tencent.qqlive"
    private val TAG = "TencentVideoHook"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {

        val packageName = lpparam.packageName
        val classLoader = lpparam.classLoader

        if (packageName != TENCENT_VIDEO_PACKAGE_NAME) {
            return
        }

        hookVideoInfo(classLoader)
        hookAdCofig(classLoader)
    }

    private fun hookVideoInfo(classLoader: ClassLoader) {
        try {
            findAndHookMethod("com.tencent.qqlive.ona.player.VideoInfo",
                classLoader,
                "isAdSkip",
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = true
                    }
                })
        } catch (t: Throwable) {
            Log.e(TAG, " hook VideoInfo failed, msg = ${t.message}", t)
            t.printStackTrace()
        }
    }

    private fun hookAdCofig(classLoader: ClassLoader) {
        val adConfigClassName = "com.tencent.qqlive.multimedia.tvkcommon.config.TVKMediaPlayerConfig\$AdConfig"
        try {
            XposedHelpers.findAndHookConstructor(
                adConfigClassName,
                classLoader,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: XC_MethodHook.MethodHookParam) {
                        val fieldNameList = arrayOf(
                            "use_ad", "pre_ad_on", "offline_video_use_ad", "loop_ad_on",
                            "postroll_use_ad", "mid_ad_on"
                        )
                        fieldNameList.forEach {
                            setBooleanField(param.thisObject, it, false)
                        }
                    }
                })
        } catch (t: Throwable) {
            Log.e(TAG, " hook VideoInfo AdConfig, msg = ${t.message}", t)
        }
    }

    private fun setBooleanField(obj: Any, fieldName: String, value: Boolean) {
        try {
            XposedHelpers.setBooleanField(obj, fieldName, value)
        } catch (t: Throwable) {
            Log.e(TAG, " setBooleanField failed,  obj --> $obj fieldName --> $fieldName ", t)
        }
    }
}