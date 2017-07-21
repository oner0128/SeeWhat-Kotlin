package com.onerous.kotlin.seewhat.data.source.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.text.TextUtils
import com.onerous.kotlin.seewhat.data.MovieData
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.data.source.MoviesDataSource
import com.onerous.kotlin.seewhat.util.formatCastsToString
import com.onerous.kotlin.seewhat.util.formatListToString
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/20.
 */
class MoviesLocalDataSource private constructor(val context: Context) : MoviesDataSource {


    companion object {
        var Instance: MoviesLocalDataSource? = null
        fun NewInstance(context: Context): MoviesLocalDataSource {
            if (Instance == null) Instance = MoviesLocalDataSource(context)
            return Instance!!
        }
    }

    val movieDBHelper = MoviesDBHelper(context)
    val sqlBrite = SqlBrite.Builder().build()
    val db = sqlBrite.wrapDatabaseHelper(movieDBHelper, Schedulers.io());
    val mMovieMapperFunction = Function<Cursor, MovieData> { it -> getMovie(it) }


    private fun getMovie(cursor: Cursor): MovieData {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_MOVIE_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_TITLE))
        val directors = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_NAME_DIRECTORS))
        val casts = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_NAME_CASTS))
        val years = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_YEARS))
        val rating = cursor.getDouble(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_RATING))
        val genres = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_GENRES))
        val imgUrl_large = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_LARGE))
        val imgUrl_medium = cursor.getString(cursor.getColumnIndexOrThrow(MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_MEDIUM))
        return MovieData(rating, title, years, imgUrl_medium, imgUrl_large, id, genres, casts, directors)
    }

    override fun getMovies(): Observable<List<MovieData>> {
        val projection: Array<String> = arrayOf(MoviesPersistenceContract.MovieEntity._ID
                , MoviesPersistenceContract.MovieEntity.COLUMN_TITLE
                , MoviesPersistenceContract.MovieEntity.COLUMN_RATING
                , MoviesPersistenceContract.MovieEntity.COLUMN_GENRES
                , MoviesPersistenceContract.MovieEntity.COLUMN_NAME_DIRECTORS
                , MoviesPersistenceContract.MovieEntity.COLUMN_NAME_CASTS
                , MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_MEDIUM)

        val sql: String = String.format("SELECT %s FROM %s ",
                TextUtils.join(",", projection),
                MoviesPersistenceContract.MovieEntity.TABLE_NAME)

        return db.createQuery(MoviesPersistenceContract.MovieEntity.TABLE_NAME, sql)
                .mapToList(mMovieMapperFunction)
    }

    override fun getMovie(movieId: String): Observable<MovieData> {
        val projection: Array<String> = arrayOf(MoviesPersistenceContract.MovieEntity._ID
                , MoviesPersistenceContract.MovieEntity.COLUMN_TITLE
                , MoviesPersistenceContract.MovieEntity.COLUMN_RATING
                , MoviesPersistenceContract.MovieEntity.COLUMN_GENRES
                , MoviesPersistenceContract.MovieEntity.COLUMN_NAME_DIRECTORS
                , MoviesPersistenceContract.MovieEntity.COLUMN_NAME_CASTS
                , MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_MEDIUM)

        val sql: String = String.format("SELECT %s FROM %s  WHERE %s LIKE ? ",
                TextUtils.join(",", projection),
                MoviesPersistenceContract.MovieEntity.TABLE_NAME, MoviesPersistenceContract.MovieEntity.COLUMN_MOVIE_ID)
        return db.createQuery(MoviesPersistenceContract.MovieEntity.TABLE_NAME, sql, movieId)
                .mapToOneOrDefault(mMovieMapperFunction, MovieData())
    }

    override fun saveMovies(moviesBean: MoviesBean) {
        moviesBean.subjects.forEach { it ->
            val contentValues = ContentValues()
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_TITLE, it.title)
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_MOVIE_ID, it.id)
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_LARGE, it.images.large)
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_MEDIUM, it.images.medium)
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_YEARS, it.year)
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_NAME_CASTS, formatCastsToString(it.casts))
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_NAME_DIRECTORS, formatCastsToString(it.directors))
            contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_GENRES, formatListToString(it.genres))
            db.insert(MoviesPersistenceContract.MovieEntity.TABLE_NAME, contentValues)
        }
    }

    override fun saveMovie(movieData: MovieData) {
        val contentValues = ContentValues()
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_TITLE, movieData.title)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_MOVIE_ID, movieData.id)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_LARGE, movieData.images_large)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_MEDIUM, movieData.images_medium)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_YEARS, movieData.year)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_NAME_CASTS, movieData.casts)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_NAME_DIRECTORS, movieData.directors)
        contentValues.put(MoviesPersistenceContract.MovieEntity.COLUMN_GENRES, movieData.genres)
        db.insert(MoviesPersistenceContract.MovieEntity.TABLE_NAME, contentValues)
    }

    override fun refreshMovies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllMovies() {
        db.delete(MoviesPersistenceContract.MovieEntity.TABLE_NAME, null)
    }

    override fun deleteMovie(movieId: String) {
        db.delete(MoviesPersistenceContract.MovieEntity.TABLE_NAME, "${MoviesPersistenceContract.MovieEntity.COLUMN_MOVIE_ID} LIKE ?", movieId)
    }
}