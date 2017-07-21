package com.onerous.kotlin.seewhat.data.source

import com.onerous.kotlin.seewhat.data.MovieData
import com.onerous.kotlin.seewhat.data.MoviesBean
import io.reactivex.Observable

/**
 * Created by rrr on 2017/7/19.
 */
interface MoviesDataSource {
    fun getMovies(): Observable<List<MovieData>>
    fun getMovie(movieId: String): Observable<MovieData>?
    fun saveMovies(moviesBean: MoviesBean)
    fun saveMovie(movieData: MovieData)
    fun refreshMovies()
    fun deleteAllMovies()
    fun deleteMovie(movieId: String)
}