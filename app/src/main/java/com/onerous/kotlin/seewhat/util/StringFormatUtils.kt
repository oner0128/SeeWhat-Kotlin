package com.onerous.kotlin.seewhat.util

import com.onerous.kotlin.seewhat.data.PersonBean

/**
 * Created by rrr on 2017/7/18.
 */


//格式化类型、国家等 List<String>
fun formatListToString(list: List<String>?): String {
    if (list != null && list.size > 0) {
        val stringBuilder = StringBuilder()
        for (i in list.indices) {
            if (i < list.size - 1) {
                stringBuilder.append(list[i]).append("/")
            } else {
                stringBuilder.append(list[i])
            }
        }
        return stringBuilder.toString()

    } else {
        return "No idea"
    }
}

//格式化主演导演等
fun formatCastsToString(casts: List<PersonBean>?): String {
    if (casts != null && casts.size > 0) {
        val stringBuilder = StringBuilder()
        for (i in casts.indices) {
            if (i < casts.size - 1) {
                stringBuilder.append(casts[i].getPersonName()).append("/")
            } else {
                stringBuilder.append(casts[i].getPersonName())
            }
        }
        return stringBuilder.toString()

    } else {
        return "No idea"
    }
}

