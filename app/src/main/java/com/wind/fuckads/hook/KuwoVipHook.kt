package com.wind.fuckads.hook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodHook.MethodHookParam
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.Exception

class KuwoVipHook : IXposedHookLoadPackage {
    private val KUWO_MUSIC_PACAKGENAME_ARRAY = arrayOf("cn.kuwo.player", "cn.kuwo.kwmusichd")

    private val TAG = "KuwoVipHook";

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val packageName = lpparam.packageName
        val classLoader = lpparam.classLoader

        if (!KUWO_MUSIC_PACAKGENAME_ARRAY.contains(packageName)) {
            return
        }

        hookVip(classLoader)  // 破解vip
        hookScreenAd(classLoader)  // 去除开屏广告
    }

    private fun hookVip(classLoader: ClassLoader) {
        try {
            XposedHelpers.findAndHookMethod(
                "cn.kuwo.mod.vipnew.ConsumptionQueryUtil",
                classLoader,
                "hasBought",
                Long::class.java,
                List::class.java,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        param.result = true
                    }
                }
            )
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        // Kuwo Music: versionCode: 9441     versionName: 9.4.4.1
        try {
            XposedHelpers.findAndHookMethod("cn.kuwo.peculiar.speciallogic.d", classLoader,
                "a", Long::class.javaPrimitiveType, MutableList::class.java,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = true
                        super.beforeHookedMethod(param)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hookScreenAd(classLoader: ClassLoader) {
        try {
            XposedHelpers.findAndHookMethod(
                "cn.kuwo.mod.mobilead.KWTableScreenAd", classLoader, "isExistTableScreen",
                XC_MethodReplacement.returnConstant(null)
            )
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        try {
            XposedHelpers.findAndHookMethod(
                "cn.kuwo.mod.mobilead.KuwoAdUrl\$AdUrlDef",
                classLoader,
                "getUrl",
                String::class.java,
                XC_MethodReplacement.returnConstant("")
            )
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}