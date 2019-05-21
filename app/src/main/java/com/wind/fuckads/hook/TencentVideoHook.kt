package com.wind.fuckads.hook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Wind
 */
class TencentVideoHook : IXposedHookLoadPackage {

    private val TENCENT_VIDEO_PACKAGE_NAME = "com.tencent.qqlive"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {

        val packageName = lpparam.packageName
        val classLoader = lpparam.classLoader

        if (packageName != TENCENT_VIDEO_PACKAGE_NAME) {
            return
        }

        findAndHookMethod("com.tencent.qqlive.ona.player.VideoInfo",
            classLoader,
            "isAdSkip",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })

    }
}