package com.onerous.kotlin.seewhat.api

import com.onerous.kotlin.seewhat.data.ZhihuBeforeNewsBean
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by rrr on 2017/7/17.
 */
interface ZhihuService {
    @GET("news/latest")
    fun getZhihuLatestNews(): Observable<ZhihuLatestNewsBean>

    @GET("news/before/{date}")
    fun getZhihuBeforeNews(@Path("date") date: String): Observable<ZhihuBeforeNewsBean>

//    @GET("news/{storyId}")
//    fun getStoryContent(@Path("storyId") storyId: Int): Observable<ZhihuStoryContentBean>
}