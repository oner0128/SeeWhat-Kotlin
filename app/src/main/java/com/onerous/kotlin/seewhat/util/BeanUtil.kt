package com.onerous.kotlin.seewhat.util

import com.google.gson.Gson

/**
 * Created by rrr on 2017/7/20.
 */
fun ChangeBeanToOtherBean(bean:Any,Otherbean:Any):Any{
    val gson = Gson()
    val tmp = gson.toJson(bean)
    return gson.fromJson(tmp, Otherbean::class.java)
}

