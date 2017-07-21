package com.onerous.kotlin.seewhat.data.source.remote

import com.onerous.kotlin.seewhat.data.MovieData
import com.onerous.kotlin.seewhat.data.MovieItem
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.MoviesDataSource
import com.onerous.kotlin.seewhat.util.ChangeBeanToOtherBean
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by rrr on 2017/7/19.
 */
object MoviesRemoteDataSource : MoviesDataSource {
    override fun saveMovie(movieData: MovieData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val SERVICE_LATENCY_IN_MILLIS: Long = 5000L
    private val MOVIES_SERVICE_DATA: MutableMap<String, MovieData>by lazy { LinkedHashMap<String, MovieData>() }
    override fun getMovies(): Observable<List<MovieData>>
            = Observable.fromIterable(MOVIES_SERVICE_DATA.values)
            .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
            .toList().toObservable()

    override fun getMovie(movieId: String): Observable<MovieData>?{
        val data: MovieData? = MOVIES_SERVICE_DATA.get(movieId)
        if (data == null) {
            return Observable.create({ e: ObservableEmitter<MovieData> -> e.onNext(data!!) }).delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
        } else {
            return Observable.empty()
        }
    }

    override fun saveMovies(moviesBean: MoviesBean) {
        Observable.fromIterable(moviesBean.subjects).map { it -> ChangeBeanToOtherBean(it, MovieData::class.java) }
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            it ->
                            it as MovieData
                            MOVIES_SERVICE_DATA.put(it.id, it)
                        },
                        { e -> Logger.e(e.toString()) })
//        moviesBean.subjects.forEach({it-> MOVIES_SERVICE_DATA.put(it.id,it)})
    }

    override fun refreshMovies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllMovies() {
        MOVIES_SERVICE_DATA.clear()
    }

    override fun deleteMovie(movieId: String) {
        MOVIES_SERVICE_DATA.remove(movieId)
    }
}