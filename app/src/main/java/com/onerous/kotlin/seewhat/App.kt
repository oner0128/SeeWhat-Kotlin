package com.onerous.kotlin.seewhat

import android.app.Application
import com.onerous.kotlin.seewhat.data.source.MoviesRepository
import com.onerous.kotlin.seewhat.util.dip2px
import com.onerous.kotlin.seewhat.util.getDeviceSize
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary

/**
 * Created by rrr on 2017/7/15.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        instance = this
        imageSize = getDeviceSize(this)
        imageSize[0] /= 2
        imageSize[1] = (imageSize[1] - dip2px(this, 58 * 2.0f)) / 2
        Logger.addLogAdapter(AndroidLogAdapter())
        mMoviesRepository= MoviesRepository.getInstance(this)
    }

    companion object {
        lateinit var mMoviesRepository : MoviesRepository
        lateinit var instance: App
            private set
        lateinit var imageSize: IntArray
            private set
    }
}

