package com.onerous.kotlin.seewhat.data.source

import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.local.MoviesLocalDataSource
import com.onerous.kotlin.seewhat.data.source.remote.MoviesRemoteDataSource
import io.reactivex.Observable
import java.util.*

/**
 * Created by rrr on 2017/7/20.
 */
object MoviesRepository {

    object InTheatersDataRepository : MoviesDataSource.DataRepository {
        override fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private val remoteSource: MoviesDataSource.RemoteInTheaters = MoviesRemoteDataSource.RemoteInTheaters
        private val localSource: MoviesDataSource.LocalInTheaters = MoviesLocalDataSource.LocalInTheaters
        private val mCachedMovies: MutableMap<String, MoviesBean.Subjects> by lazy { LinkedHashMap<String, MoviesBean.Subjects>() }
        private var mCacheIsDirty = false

        override fun getMovies(): Observable<List<MoviesBean.Subjects>> {
            // Respond immediately with cache if available and not dirty
            if (!mCacheIsDirty) {
                return Observable.fromIterable(mCachedMovies.values).toList().toObservable()
            }

            if (mCacheIsDirty) {
                return getAndSaveRemoteTasks()
            } else {
                // Query the local storage if available. If not, query the network.
                return getAndCacheLocalTasks()
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
            movies.forEach { it -> mCachedMovies.put(it.id, it) }
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

        private fun getAndCacheLocalTasks(): Observable<List<MoviesBean.Subjects>> = localSource.getMovies()
                .flatMap {
                    movies: List<MoviesBean.Subjects> ->
                    Observable.fromIterable(movies)
                            .doOnNext { it -> mCachedMovies.put(it.id, it) }
                            .toList().toObservable()
                }

        private fun getAndSaveRemoteTasks(): Observable<List<MoviesBean.Subjects>> = remoteSource.getMovies()
                .flatMap {
                    movies: List<MoviesBean.Subjects> ->
                    Observable.fromIterable(movies)
                            .doOnNext(
                                    { it ->
                                        localSource.saveMovie(it)
                                        mCachedMovies.put(it.id, it)
                                    })
                            .doOnComplete { mCacheIsDirty = false }
                            .toList()
                            .toObservable()
                }

        private fun getMovieWithId(movieId: String): MoviesBean.Subjects? {
            if (mCachedMovies.isEmpty()) {
                return null
            } else {
                return mCachedMovies.get(movieId)
            }
        }
    }

    object Top250DataRepository : MoviesDataSource.DataRepository {

        private val remoteSource = MoviesRemoteDataSource.RemoteTop250
        private val localSource = MoviesLocalDataSource.LocalInTheaters
        private var start = 0
        private val mCachedMovies: MutableMap<String, MoviesBean.Subjects> by lazy { LinkedHashMap<String, MoviesBean.Subjects>() }
        private var mCacheIsDirty = false

        override fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>> {
            // Respond immediately with cache if available and not dirty
            if (!mCacheIsDirty) {
                return Observable.fromIterable(mCachedMovies.values).toList().toObservable()
            }

            if (mCacheIsDirty) {
                return getAndSaveRemoteTasks(start, count)
            } else {
                // Query the local storage if available. If not, query the network.
                return getAndCacheLocalTasks()
            }
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
            movies.forEach { it -> mCachedMovies.put(it.id, it) }
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

        private fun getAndCacheLocalTasks(): Observable<List<MoviesBean.Subjects>> = localSource.getMovies()
                .flatMap {
                    movies: List<MoviesBean.Subjects> ->
                    Observable.fromIterable(movies)
                            .doOnNext { it -> mCachedMovies.put(it.id, it) }
                            .toList().toObservable()
                }

        private fun getAndSaveRemoteTasks(start: Int, count: Int): Observable<List<MoviesBean.Subjects>>
                = remoteSource.getMovies(start, count)
                .flatMap {
                    movies: List<MoviesBean.Subjects> ->
                    Observable.fromIterable(movies)
                            .doOnNext(
                                    { it ->
                                        localSource.saveMovie(it)
                                        mCachedMovies.put(it.id, it)
                                    })
                            .doOnComplete { mCacheIsDirty = false }
                            .toList()
                            .toObservable()
                }

        private fun getMovieWithId(movieId: String): MoviesBean.Subjects? {
            if (mCachedMovies.isEmpty()) {
                return null
            } else {
                return mCachedMovies.get(movieId)
            }
        }
    }
}