package com.onerous.kotlin.seewhat.data.source

import android.content.Context
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.local.MoviesLocalDataSource
import com.onerous.kotlin.seewhat.data.source.remote.MoviesRemoteDataSource
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by rrr on 2017/7/20.
 */
class MoviesRepository private constructor(context: Context) {
    private val mMoviesLocalDataSource = MoviesLocalDataSource.getInstance(context)

    val InTheatersRepository: InTheatersDataRepository by lazy { InTheatersDataRepository() }
    val Top250Repository: Top250DataRepository by lazy { Top250DataRepository() }


    companion object {
        private var instance: MoviesRepository? = null
        fun getInstance(context: Context): MoviesRepository {
            if (instance == null) {
                synchronized(MoviesRepository.javaClass) {
                    if (instance == null)
                        instance = MoviesRepository(context)
                }
            }
            return instance!!
        }
    }

    inner class InTheatersDataRepository : MoviesDataSource.DataRepository {

        override fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private val remoteSource: MoviesDataSource.RemoteInTheaters = MoviesRemoteDataSource.RemoteInTheaters
        private val localSource: MoviesDataSource.LocalInTheaters = mMoviesLocalDataSource.mLocalInTheaters
        private val mCachedMovies: MutableMap<String, MoviesBean.Subjects> by lazy { LinkedHashMap<String, MoviesBean.Subjects>() }
        var mCacheIsDirty = false

        override fun getMovies(): Observable<List<MoviesBean.Subjects>> {
            // Respond immediately with cache if available and not dirty
            if (!mCacheIsDirty && mCachedMovies.isNotEmpty()) {
//                Logger.v("from cache")
                return Observable.fromIterable(mCachedMovies.values)
                        .subscribeOn(Schedulers.computation())
                        .toList().toObservable()
            }
            var remoteMovies = getAndSaveRemoteMovies()
            if (mCacheIsDirty) {
                Logger.v("from Remote")
                return remoteMovies
            } else {
                // Query the local storage if available. If not, query the network.
                Logger.v("from Local")
                var localMovies = getAndCacheLocalMovies()
//                return localMovies
                return Observable.concat(localMovies,remoteMovies)
                        .filter { movies -> Logger.v("${movies.size}")
                            movies.isNotEmpty() }
                        .take(1)

            }
        }

        override fun getMovie(movieId: String): Observable<MoviesBean.Subjects> {
            //qeury in mCachedMovies
            val movie = getMovieWithId(movieId)
            // Respond immediately with cache if available
            if (movie != null) {
                return Observable.just(movie)
            }
            // Load from server/persisted if needed.

            // Is the movie in the local data source? If not, query the network.
            return localSource.getMovie(movieId);
        }

        override fun saveMovies(movies: List<MoviesBean.Subjects>) {
            localSource.saveMovies(movies)
            movies.forEach { movie -> if (!mCachedMovies.containsKey(movie.id)) mCachedMovies.put(movie.id, movie) }
        }

        override fun saveMovie(movie: MoviesBean.Subjects) {
            localSource.saveMovie(movie)
            if (!mCachedMovies.containsKey(movie.id)) mCachedMovies.put(movie.id, movie)
        }

        override fun refreshMovies() {
            mCacheIsDirty = true
        }

        override fun deleteAllMovies() {
            localSource.deleteAllMovies()
            mCachedMovies.clear()
        }

        override fun deleteMovie(movieId: String) {
            localSource.deleteMovie(movieId)
            mCachedMovies.remove(movieId)
        }

        private fun getAndCacheLocalMovies(): Observable<List<MoviesBean.Subjects>> {
            return localSource
                    .getMovies()
                    .doOnNext { movies ->
                        movies.forEach { movie ->
                            if (!mCachedMovies.containsKey(movie.id)) mCachedMovies.put(movie.id, movie)
                        }
                    }.subscribeOn(Schedulers.computation())
        }

        private fun getAndSaveRemoteMovies(): Observable<List<MoviesBean.Subjects>> {
            return remoteSource
                    .getMovies()
                    .doOnNext { movies -> saveMovies(movies) }
                    .doOnComplete { mCacheIsDirty = false }
                    .subscribeOn(Schedulers.io())
        }

        private fun getMovieWithId(movieId: String): MoviesBean.Subjects? {
            if (mCachedMovies.isEmpty()) {
                return null
            } else {
                return mCachedMovies.get(movieId)
            }
        }
    }

    inner class Top250DataRepository : MoviesDataSource.DataRepository {

        private val remoteSource = MoviesRemoteDataSource.RemoteTop250
        private val localSource = mMoviesLocalDataSource.mLocalTop250
        private val mCachedMovies: MutableMap<String, MoviesBean.Subjects> by lazy { LinkedHashMap<String, MoviesBean.Subjects>() }
        var mCacheIsDirty = false

        override fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>> {
//            if (mCachedMovies.size <= start) {//need load more moive
//                Logger.v("Cached.size${mCachedMovies.size}--$start need load more from remote")
//                return getAndSaveRemoteMovies(start, count)
//            }
            // Respond immediately with cache if available and not dirty
//            else {
            if (!mCacheIsDirty && mCachedMovies.size > start) {
                Logger.v("from cache Cached.size${mCachedMovies.size} --$start")
//                val list = arrayListOf<MoviesBean.Subjects>()
//                var iterator = mCachedMovies.iterator()
//                for (i in start..start + count) {
//                    list.add(iterator.next().value)
//                }
//                return Observable.fromIterable(list)
//                        .subscribeOn(Schedulers.io())
//                        .toList().toObservable()
                return Observable.fromIterable(mCachedMovies.values)
                        .skip(start.toLong()).take(count.toLong())
                        .subscribeOn(Schedulers.computation())
                        .toList().toObservable()
            }

            var remoteMovies = getAndSaveRemoteMovies(start, count).doOnNext({it->Logger.v(it.get(0).title)})
            if (mCacheIsDirty) {
                Logger.v("from Remote")
                return remoteMovies
            } else {
                // Query the local storage if available. If not, query the network.
                Logger.v("from Local")
                var localMovies = getAndCacheLocalMovies(start, count)
//                return localMovies
                return Observable.concat(localMovies, remoteMovies)
                        .filter { moives->Logger.v("moives-size:${moives.size}")
                            moives.size>=count }
                        .take(1)
            }
//            }
        }

        override fun getMovies(): Observable<List<MoviesBean.Subjects>> {
            TODO()
        }

        override fun getMovie(movieId: String): Observable<MoviesBean.Subjects> {
            //qeury in mCachedMovies
            val movie = getMovieWithId(movieId)
            // Respond immediately with cache if available
            if (movie != null) {
                return Observable.just(movie)
            }
            // Load from server/persisted if needed.

            // Is the movie in the local data source? If not, query the network.
            return localSource.getMovie(movieId);
        }

        override fun saveMovies(movies: List<MoviesBean.Subjects>) {
            localSource.saveMovies(movies)
            movies.forEach { it -> if (!mCachedMovies.containsKey(it.id)) mCachedMovies.put(it.id, it) }
        }

        override fun saveMovie(movie: MoviesBean.Subjects) {
            localSource.saveMovie(movie)
            mCachedMovies.put(movie.id, movie)
        }

        override fun refreshMovies() {
            mCacheIsDirty = true
        }

        override fun deleteAllMovies() {
            localSource.deleteAllMovies()
            mCachedMovies.clear()
        }

        override fun deleteMovie(movieId: String) {
            localSource.deleteMovie(movieId)
            mCachedMovies.remove(movieId)
        }

        private fun getAndCacheLocalMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>>
                = localSource.getMovies(start, count)
//                .filter { movies -> movies.isNotEmpty() }
                .doOnNext {
                    movies ->
                    Logger.v("getAndCacheLocalMovies:${movies.size}")
                    movies.forEach { it -> if (!mCachedMovies.containsKey(it.id)) mCachedMovies.put(it.id, it) }
                }
//                .subscribeOn(Schedulers.computation())

        private fun getAndSaveRemoteMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>>
                = remoteSource.getMovies(start, count)
                .doOnNext {
                    movies ->
                    Logger.v("getAndSaveRemoteMovies")
                    saveMovies(movies)
                }.doOnComplete { mCacheIsDirty = false }
//                .subscribeOn(Schedulers.io())

        private fun getMovieWithId(movieId: String): MoviesBean.Subjects? {
            if (mCachedMovies.isEmpty()) {
                return null
            } else {
                return mCachedMovies.get(movieId)
            }
        }
    }
}