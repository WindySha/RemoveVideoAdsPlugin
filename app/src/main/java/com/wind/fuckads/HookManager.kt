package com.wind.fuckads

import com.wind.fuckads.hook.IQiyiHook
import com.wind.fuckads.hook.TencentVideoHook
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

/**
 * Created by Wind
 */
object HookManager {

    private val hookClassList = hashSetOf<IXposedHookLoadPackage>()

    fun startHook(lpparam: XC_LoadPackage.LoadPackageParam) {
        hookClassList.forEach { it.handleLoadPackage(lpparam) }
    }

    fun registHookClass(hookerList: List<IXposedHookLoadPackage>) {
        hookClassList.addAll(hookerList)
    }

    init {
        registHookClass(
            arrayListOf(
                TencentVideoHook(),
                IQiyiHook()
            )
        )
    }
}