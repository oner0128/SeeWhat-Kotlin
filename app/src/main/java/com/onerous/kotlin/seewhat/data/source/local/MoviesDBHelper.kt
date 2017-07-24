package com.onerous.kotlin.seewhat.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.orhanobut.logger.Logger


/**
 * Created by rrr on 2017/7/20.
 */
val DATABASE_VERSION = 1

val DATABASE_NAME = "Moives.db"

private val TEXT_TYPE = " TEXT"

private val DOUBLE_TYPE = " REAL"

private val COMMA_SEP = ","

private val UNIQUE = " UNIQUE"

private val SQL_CREATE_INTHEATERS_ENTRIES =
        "CREATE TABLE " + MoviesPersistenceContract.InTheatersEntity.TABLE_NAME + " (" +
                MoviesPersistenceContract.InTheatersEntity._ID + TEXT_TYPE + " PRIMARY KEY," +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_MOVIE_ID + TEXT_TYPE + UNIQUE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_DIRECTORS + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_NAME_CASTS + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_RATING + DOUBLE_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_GENRES + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_YEARS + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_IMAGE_URL_LARGE + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.InTheatersEntity.COLUMN_IMAGE_URL_MEDIUM + TEXT_TYPE +
                " )"

private val SQL_CREATE_TOP250_ENTRIES =
        "CREATE TABLE " + MoviesPersistenceContract.Top250Entity.TABLE_NAME + " (" +
                MoviesPersistenceContract.Top250Entity._ID + TEXT_TYPE + " PRIMARY KEY," +
                MoviesPersistenceContract.Top250Entity.COLUMN_MOVIE_ID + TEXT_TYPE + UNIQUE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_NAME_DIRECTORS + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_NAME_CASTS + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_RATING + DOUBLE_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_GENRES + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_YEARS + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_IMAGE_URL_LARGE + TEXT_TYPE + COMMA_SEP +
                MoviesPersistenceContract.Top250Entity.COLUMN_IMAGE_URL_MEDIUM + TEXT_TYPE +
                " )"

class MoviesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        Logger.v("execSQL")
        db.execSQL(SQL_CREATE_INTHEATERS_ENTRIES)
        db.execSQL(SQL_CREATE_TOP250_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Not required as at version 1
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Not required as at version 1
    }
}