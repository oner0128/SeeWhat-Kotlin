package com.onerous.kotlin.seewhat.data.source

import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.data.MovieData
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.local.MoviesLocalDataSource
import com.onerous.kotlin.seewhat.data.source.remote.MoviesRemoteDataSource
import com.onerous.kotlin.seewhat.util.ChangeBeanToOtherBean
import io.reactivex.Observable
import java.util.*

/**
 * Created by rrr on 2017/7/20.
 */
object MoviesRepository : MoviesDataSource {

    val remoteSource: MoviesDataSource = MoviesRemoteDataSource
    val localSource: MoviesDataSource = MoviesLocalDataSource.NewInstance(App.instance)
    var mCachedMovies: MutableMap<String, MovieData>? = null
    var mCacheIsDirty = false

    override fun getMovies(): Observable<List<MovieData>> {
        // Respond immediately with cache if available and not dirty
        if (mCachedMovies != null && !mCacheIsDirty) {
            return Observable.fromIterable(mCachedMovies!!.values).toList().toObservable()
        } else if (mCachedMovies == null) {
            mCachedMovies = LinkedHashMap<String, MovieData>()
        }

        val remoteMovies = getAndSaveRemoteTasks()

        if (mCacheIsDirty) {
            return remoteMovies
        } else {
            // Query the local storage if available. If not, query the network.
            return getAndCacheLocalTasks()
        }
    }

    private fun getAndCacheLocalTasks(): Observable<List<MovieData>> = localSource.getMovies()
            .flatMap {
                movies: List<MovieData> ->
                Observable.fromIterable(movies)
                        .doOnNext { it -> mCachedMovies?.put(it.id, it) }
                        .toList().toObservable()
            }


    private fun getAndSaveRemoteTasks(): Observable<List<MovieData>> = remoteSource.getMovies()
            .flatMap {
                movies: List<MovieData> ->
                Observable.fromIterable(movies)
                        .doOnNext(
                                { it ->
                                    localSource.saveMovie(it)
                                    mCachedMovies?.put(it.id, it)
                                })
                        .doOnComplete { mCacheIsDirty = false }
                        .toList()
                        .toObservable()
            }

    override fun saveMovie(movieData: MovieData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getMovieWithId(movieId: String): MovieData? {
        if (mCachedMovies == null || mCachedMovies!!.isEmpty()) {
            return null
        } else {
            return mCachedMovies!!.get(movieId)
        }
    }

    override fun getMovie(movieId: String): Observable<MovieData>? {
        //qeury in mCachedMovies
        val movie=getMovieWithId(movieId)
        // Respond immediately with cache if available
        if (movie != null) {
            return Observable.just(movie)
        }
        // Load from server/persisted if needed.

        // Do in memory cache update to keep the app UI up to date
        if (mCachedMovies == null) {
            mCachedMovies = LinkedHashMap<String, MovieData>()
        }

        // Is the task in the local data source? If not, query the network.
        val localTask = localSource.getMovie(movieId)
        val remoteTask = remoteSource
                .getMovie(movieId)
                ?.doOnNext({ movie ->
                    localSource.saveMovie(movie)
                    mCachedMovies!!.put(movie.id, movie)
                })
        return Observable.concat(localTask, remoteTask).singleOrError()
                .map { movie ->
                    if (movie == null) {
                        throw NoSuchElementException("No task found with movieId " + movieId)
                    }
                    movie
                }.toObservable();
    }

    override fun saveMovies(moviesBean: MoviesBean) {
        remoteSource.saveMovies(moviesBean)
        localSource.saveMovies(moviesBean)

        // Do in memory cache update to keep the app UI up to date
        if (mCachedMovies == null) {
            mCachedMovies = LinkedHashMap<String, MovieData>()
        }
        moviesBean.subjects.forEach { it -> mCachedMovies!!.put(it.id, ChangeBeanToOtherBean(it, MovieData::class.java) as MovieData) }
    }

    override fun refreshMovies() {
        mCacheIsDirty = true
    }

    override fun deleteAllMovies() {
        remoteSource.deleteAllMovies()
        localSource.deleteAllMovies()

        if (mCachedMovies == null) {
            mCachedMovies = LinkedHashMap<String, MovieData>()
        }
        mCachedMovies!!.clear()
    }

    override fun deleteMovie(movieId: String) {
        remoteSource.deleteMovie(movieId)
        localSource.deleteMovie(movieId)
        mCachedMovies?.remove(movieId)
    }
}