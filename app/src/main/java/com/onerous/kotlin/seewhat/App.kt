package com.onerous.kotlin.seewhat

import android.app.Application
import com.onerous.kotlin.seewhat.util.DensityUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by rrr on 2017/7/15.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        imageSize = DensityUtil.getDeviceInfo(this)
        imageSize[0] /= 2
        imageSize[1] = (imageSize[1] - DensityUtil.dip2px(this, 58 * 2.0f)) / 2
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    companion object {
        lateinit var instance: App
            private set
        lateinit var imageSize: IntArray
            private set
    }
}

