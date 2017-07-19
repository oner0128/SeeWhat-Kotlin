package com.onerous.kotlin.seewhat.util

import android.content.Context


private val deviceWidthHeight = IntArray(2)

fun getDeviceSize(context: Context): IntArray {
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

