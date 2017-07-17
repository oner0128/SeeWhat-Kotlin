package com.onerous.kotlin.seewhat.api

import com.onerous.kotlin.seewhat.data.MoviesBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rrr on 2017/7/15.
 */
interface DouBanService {
    @GET("in_theaters")
    fun getInTheatersMovies():Observable<MoviesBean>

    @GET("top250")
    abstract fun getTop250Movies(@Query("start") start: Int, @Query("count") count: Int): Observable<MoviesBean>
}