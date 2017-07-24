package com.onerous.kotlin.seewhat.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.MoviesDataSource
import com.onerous.kotlin.seewhat.util.formatCastsToString
import com.onerous.kotlin.seewhat.util.formatListToString
import com.orhanobut.logger.Logger
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/20.
 */
class MoviesLocalDataSource private constructor(val context: Context) {

    val movieDBHelper = MoviesDBHelper(context)
    val sqlBrite = SqlBrite.Builder().build()
    val db = sqlBrite.wrapDatabaseHelper(movieDBHelper, Schedulers.io());
    val mMovieMapperFunction = Function<Cursor, MoviesBean.Subjects> { it -> getMovie(it) }
    val mLocalInTheaters = LocalInTheaters()
    val mLocalTop250 = LocalTop250()

    companion object {
        private var instance: MoviesLocalDataSource? = null
        fun getInstance(context: Context): MoviesLocalDataSource {
            if (instance == null) {
                synchronized(MoviesLocalDataSource.javaClass) {
                    if (instance == null)
                        instance = MoviesLocalDataSource(context)
                }
            }
            return instance!!
        }
    }

    inner class LocalInTheaters : MoviesDataSource.LocalInTheaters {

        val tableName: String = MoviesPersistenceContract.InTheatersEntity.TABLE_NAME

        override fun getMovies(): Observable<List<MoviesBean.Subjects>> {
            return getMovies(tableName)
        }

        override fun getMovie(movieId: String): Observable<MoviesBean.Subjects> {
            return getMovie(tableName, movieId)
        }

        override fun saveMovies(movies: List<MoviesBean.Subjects>) {
            return saveMovies(tableName, movies)
        }

        override fun saveMovie(movie: MoviesBean.Subjects) {
            return saveMovie(tableName, movie)
        }

        override fun refreshMovies() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun deleteAllMovies() {
            deleteAllMovies(MoviesPersistenceContract.InTheatersEntity.TABLE_NAME)
        }

        override fun deleteMovie(movieId: String) {
            deleteMovie(tableName, movieId)
        }


    }

    inner class LocalTop250 : MoviesDataSource.LocalTop250 {
        val tableName: String = MoviesPersistenceContract.Top250Entity.TABLE_NAME

        override fun getMovies(start: Int, count: Int): Observable<List<MoviesBean.Subjects>> {
//            val sql: String = String.format("SELECT %s FROM %s WHERE %s BETWEEN %s AND %s"
//                    , TextUtils.join(",", projection)
//                    , tableName
//                    , MoviesPersistenceContract.Top250Entity._ID
//                    , start, count)
//            val sql: String = String.format("SELECT * FROM %s WHERE %s BETWEEN %s AND %s"
//                    , tableName
//                    , MoviesPersistenceContract.Top250Entity._ID
//                    , start
//                    , start + count)
            val sql: String = String.format("SELECT * FROM %s "
                    , tableName)
            Logger.v("getMovies: $sql")
            return db.createQuery(tableName, sql)
                    .mapToList(mMovieMapperFunction)
        }

        override fun getMovie(movieId: String): Observable<MoviesBean.Subjects> {
            return getMovie(tableName, movieId)
        }

        override fun saveMovies(movies: List<MoviesBean.Subjects>) {
            return saveMovies(tableName, movies)
        }

        override fun saveMovie(movie: MoviesBean.Subjects) {
            return saveMovie(tableName, movie)
        }

        override fun refreshMovies() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun deleteAllMovies() {
            deleteAllMovies(MoviesPersistenceContract.Top250Entity.TABLE_NAME)
        }

        override fun deleteMovie(movieId: String) {
            deleteMovie(tableName, movieId)
        }


    }

    private fun getMovie(cursor: Cursor): MoviesBean.Subjects {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_MOVIE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_TITLE))

        val years = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_YEARS))
        val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_RATING))
        val imgUrl_large = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_IMAGE_URL_LARGE))
        val imgUrl_medium = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_IMAGE_URL_MEDIUM))
        val genres = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_GENRES))
                .split("/")
        val casts = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_CASTS))
                .split("/").map { it -> MoviesBean.Subjects.Casts(name = it) }.toList()
        val directors = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_DIRECTORS))
                .split("/").map { it -> MoviesBean.Subjects.Directors(name = it) }.toList()
        return MoviesBean.Subjects(
                rating = MoviesBean.Subjects.Rating(average = rating)
                , title = title
                , images = MoviesBean.Subjects.Images(medium = imgUrl_medium, large = imgUrl_large)
                , year = years, id = id
                , genres = genres, casts = casts, directors = directors)
    }

    fun getMovies(tableName: String): Observable<List<MoviesBean.Subjects>> {
//        val projection: Array<String> = arrayOf(MoviesPersistenceContract.InTheatersEntity._ID
//                , MoviesPersistenceContract.InTheatersEntity.COLUMN_TITLE
//                , MoviesPersistenceContract.InTheatersEntity.COLUMN_RATING
//                , MoviesPersistenceContract.InTheatersEntity.COLUMN_GENRES
//                , MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_DIRECTORS
//                , MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_CASTS
//                , MoviesPersistenceContract.InTheatersEntity.COLUMN_IMAGE_URL_LARGE)

//        val sql: String = String.format("SELECT %s FROM %s ",
//                TextUtils.join(",", projection),
//                tableName)
        val sql: String = String.format("SELECT * FROM %s ",
                tableName)
        return db.createQuery(tableName, sql)
                .mapToList(mMovieMapperFunction)
    }

    fun getMovie(tableName: String, movieId: String): Observable<MoviesBean.Subjects> {
        val projection: Array<String> = arrayOf(MoviesPersistenceContract.InTheatersEntity._ID
                , MoviesPersistenceContract.InTheatersEntity.COLUMN_TITLE
                , MoviesPersistenceContract.InTheatersEntity.COLUMN_RATING
                , MoviesPersistenceContract.InTheatersEntity.COLUMN_GENRES
                , MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_DIRECTORS
                , MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_CASTS
                , MoviesPersistenceContract.InTheatersEntity.COLUMN_IMAGE_URL_MEDIUM)

        val sql: String = String.format("SELECT %s FROM %s  WHERE %s LIKE ? ",
                TextUtils.join(",", projection),
                tableName, MoviesPersistenceContract.InTheatersEntity.COLUMN_MOVIE_ID)
        return db.createQuery(tableName, sql, movieId)
                .mapToOne(mMovieMapperFunction)
    }

    fun saveMovies(tableName: String, movies: List<MoviesBean.Subjects>) {
        val transaction = db.newTransaction()
        try {
            movies.forEach { movie ->
                val contentValues = ContentValues()
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_TITLE, movie.title)
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_MOVIE_ID, movie.id)
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_RATING, movie.rating.average)
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_IMAGE_URL_LARGE, movie.images.large)
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_IMAGE_URL_MEDIUM, movie.images.medium)
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_YEARS, movie.year)
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_NAME_CASTS, formatCastsToString(movie.casts))
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_NAME_DIRECTORS, formatCastsToString(movie.directors))
                contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_GENRES, formatListToString(movie.genres))
                db.insert(tableName, contentValues, SQLiteDatabase.CONFLICT_IGNORE)
            }
            transaction.markSuccessful()
        } finally {
            transaction.end()
        }

    }

    fun saveMovie(tableName: String, movie: MoviesBean.Subjects) {
        val contentValues = ContentValues()
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_TITLE, movie.title)
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_MOVIE_ID, movie.id)
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_RATING, movie.rating.average)
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_IMAGE_URL_LARGE, movie.images.large)
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_IMAGE_URL_MEDIUM, movie.images.medium)
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_YEARS, movie.year)
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_NAME_CASTS, formatCastsToString(movie.casts))
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_NAME_DIRECTORS, formatCastsToString(movie.directors))
        contentValues.put(MoviesPersistenceContract.Top250Entity.COLUMN_GENRES, formatListToString(movie.genres))
        db.insert(tableName, contentValues, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun deleteAllMovies(tableName: String) {
//        db.delete(MoviesPersistenceContract.InTheatersEntity.TABLE_NAME, null)
        db.delete(tableName, null)
    }

    fun deleteMovie(tableName: String, movieId: String) {
//        db.delete(MoviesPersistenceContract.InTheatersEntity.TABLE_NAME, "${MoviesPersistenceContract.InTheatersEntity.COLUMN_MOVIE_ID} LIKE ?", movieId)
        db.delete(tableName, "${MoviesPersistenceContract.Top250Entity.COLUMN_MOVIE_ID} LIKE ?", movieId)
    }
}