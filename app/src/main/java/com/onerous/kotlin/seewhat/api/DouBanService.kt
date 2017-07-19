package com.onerous.kotlin.seewhat.api

import com.onerous.kotlin.seewhat.data.MovieDetailBean
import com.onerous.kotlin.seewhat.data.MoviesBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by rrr on 2017/7/15.
 */
interface DouBanService {
    @GET("in_theaters")
    fun getInTheatersMovies():Observable<MoviesBean>

    @GET("top250")
    fun getTop250Movies(@Query("start") start: Int, @Query("count") count: Int): Observable<MoviesBean>

    @GET("subject/{id}")
    fun getMovieDetail(@Path("id") id: String): Observable<MovieDetailBean>
}