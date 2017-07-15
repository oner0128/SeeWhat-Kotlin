package com.onerous.kotlin.seewhat.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by rrr on 2017/4/22.
 */

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

