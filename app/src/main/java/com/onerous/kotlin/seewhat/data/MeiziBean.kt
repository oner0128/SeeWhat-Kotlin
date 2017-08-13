package com.onerous.kotlin.seewhat.data

/**
 * Created by oner0128 on 2017/8/12.
 */
data class MeiziBean(var error: Boolean,
                     var results: List<Results>) {
    data class Results(var _id: String,
                       var createdAt: String,
                       var desc: String,
                       var publishedAt: String,
                       var source: String,
                       var type: String,
                       var url: String,
                       var used: Boolean,
                       var who: String)
}