package com.onerous.kotlin.seewhat.util

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager

object DensityUtil {
    private val deviceWidthHeight = IntArray(2)

    fun getDeviceInfo(context: Context): IntArray {
        if (deviceWidthHeight[0] == 0 && deviceWidthHeight[1] == 0) {
            val metrics = context.resources.displayMetrics
            deviceWidthHeight[0] = metrics.widthPixels
            deviceWidthHeight[1] = metrics.heightPixels
        }
        return deviceWidthHeight
    }

    /**
     * @param context 上下文
     * *
     * @param dpValue dp数值
     * *
     * @return dp to  px
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()

    }

    /**
     * 获取屏幕尺寸
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    fun getScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return Point(display.width, display.height)
        } else {
            val point = Point()
            display.getSize(point)
            return point
        }
    }

    /**
     * @param context 上下文
     * *
     * @param pxValue px的数值
     * *
     * @return px to dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()

    }
}
