package com.onerous.kotlin.seewhat.api

import com.onerous.kotlin.seewhat.data.MoviesBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by rrr on 2017/7/15.
 */
interface DouBanService {
    @GET("in_theaters")
    fun getInTheatersMovies():Observable<MoviesBean>
}