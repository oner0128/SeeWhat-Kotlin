package com.onerous.kotlin.seewhat.data.source.remote

import com.onerous.kotlin.seewhat.api.ApiService
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.MoviesDataSource
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


/**
 * Created by rrr on 2017/7/19.
 */
object MoviesRemoteDataSource {
    object RemoteTop250 : MoviesDataSource.RemoteTop250 {
        override fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>> {
            Logger.v("RemoteTop250:$start-$count")
            return ApiService.douBanService
                    .getTop250Movies(start, count)
                    .subscribeOn(Schedulers.io())
                    .map { it -> it.subjects }
        }
    }

    object RemoteInTheaters : MoviesDataSource.RemoteInTheaters {
        override fun getMovies(): Observable<List<MoviesBean.Subjects>> {
            Logger.v("RemoteInTheaters")
            return ApiService.douBanService
                    .getInTheatersMovies()
                    .subscribeOn(Schedulers.io())
                    .map { it -> it.subjects }
        }
    }
}