package com.onerous.kotlin.seewhat

import android.app.Application
import com.onerous.kotlin.seewhat.util.DensityUtil

/**
 * Created by rrr on 2017/7/15.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        imageSize = DensityUtil.getDeviceInfo(this)
        imageSize[0] /= 2;
        imageSize[1] = (imageSize[1] - DensityUtil.dip2px(this, 58 * 2.0f)) / 2;
    }

    companion object {
        lateinit var app: App
        lateinit var imageSize: IntArray
    }
}

