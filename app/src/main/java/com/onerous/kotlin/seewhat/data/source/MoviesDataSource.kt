package com.onerous.kotlin.seewhat.data.source

import com.onerous.kotlin.seewhat.data.MoviesBean
import io.reactivex.Observable

/**
 * Created by rrr on 2017/7/19.
 */
interface MoviesDataSource {
    interface DataRepository {
        fun getMovies(): Observable<List<MoviesBean.Subjects>>
        fun getMovies(start: Int,count: Int): Observable<List<MoviesBean.Subjects>>
        fun getMovie(movieId: String): Observable<MoviesBean.Subjects>
        fun saveMovies(movies: List<MoviesBean.Subjects>)
        fun saveMovie(movie: MoviesBean.Subjects)
        fun refreshMovies()
        fun deleteAllMovies()
        fun deleteMovie(movieId: String)
    }

    interface RemoteInTheaters {
        fun getMovies(): Observable<List<MoviesBean.Subjects>>
    }

    interface LocalInTheaters {
        fun getMovies(): Observable<List<MoviesBean.Subjects>>
        fun getMovie(movieId: String): Observable<MoviesBean.Subjects>
        fun saveMovies(movies: List<MoviesBean.Subjects>)
        fun saveMovie(movie: MoviesBean.Subjects)
        fun refreshMovies()
        fun deleteAllMovies()
        fun deleteMovie(movieId: String)
    }

    interface RemoteTop250 {
        fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>>
    }

    interface LocalTop250 {
        fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>>
        fun getMovie(movieId: String): Observable<MoviesBean.Subjects>
        fun saveMovies(list: List<MoviesBean.Subjects>)
        fun saveMovie(movieData: MoviesBean.Subjects)
        fun refreshMovies()
        fun deleteAllMovies()
        fun deleteMovie(movieId: String)
    }

}