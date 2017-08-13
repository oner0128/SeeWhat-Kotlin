package com.onerous.kotlin.seewhat.api

import com.onerous.kotlin.seewhat.data.MeiziBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by oner0128 on 2017/8/12.
 */
interface GankService {
    @GET("api/data/福利/10/{page}")
    fun getMeizi(@Path("page") page: Int): Observable<MeiziBean>
}
