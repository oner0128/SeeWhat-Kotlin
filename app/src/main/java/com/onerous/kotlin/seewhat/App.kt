package com.onerous.kotlin.seewhat

import android.app.Application

/**
 * Created by rrr on 2017/7/15.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        var app: App? = null
    }
}
