package com.onerous.kotlin.seewhat.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Created by rrr on 2017/7/20.
 */
class MoviesDBHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1

        val DATABASE_NAME = "Moives.db"

        private val TEXT_TYPE = " TEXT"

        private val DOUBLE_TYPE = " REAL"

        private val COMMA_SEP = ","

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + MoviesPersistenceContract.MovieEntity.TABLE_NAME + " (" +
                        MoviesPersistenceContract.MovieEntity._ID + TEXT_TYPE + " PRIMARY KEY," +
                        MoviesPersistenceContract.MovieEntity.COLUMN_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_NAME_DIRECTORS + TEXT_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_NAME_CASTS + TEXT_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_RATING + DOUBLE_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_GENRES + TEXT_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_YEARS + TEXT_TYPE + COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_LARGE + TEXT_TYPE +COMMA_SEP +
                        MoviesPersistenceContract.MovieEntity.COLUMN_IMAGE_URL_MEDIUM + TEXT_TYPE +
                        " )"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Not required as at version 1
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Not required as at version 1
    }
}