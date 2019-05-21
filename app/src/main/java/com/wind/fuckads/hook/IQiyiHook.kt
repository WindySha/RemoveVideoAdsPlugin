package com.wind.fuckads.hook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Wind
 */
class IQiyiHook : IXposedHookLoadPackage {

    private val IQIYI_PACKAGE_NAME = "com.qiyi.video"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {

        val packageName = lpparam.packageName
        val classLoader = lpparam.classLoader

        if (packageName != IQIYI_PACKAGE_NAME) {
            return
        }

        XposedHelpers.findAndHookMethod("com.iqiyi.video.qyplayersdk.player.state.StateManager",
            classLoader,
            "updateVideoType",
            Int::class.java,
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam?): Any? {
                    return null
                }
            })
    }
}