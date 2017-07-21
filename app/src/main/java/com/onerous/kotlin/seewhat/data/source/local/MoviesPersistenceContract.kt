package com.onerous.kotlin.seewhat.data.source.local

import android.provider.BaseColumns

/**
 * Created by rrr on 2017/7/20.
 */
object MoviesPersistenceContract {
    object MovieEntity {
        const val _COUNT = "_count"
        const val _ID = "_id"
        const val TABLE_NAME = "Movie"
        const val COLUMN_MOVIE_ID = "movieid"
        const val COLUMN_TITLE = "title"
        const val COLUMN_NAME_DIRECTORS = "directors"
        const val COLUMN_NAME_CASTS = "casts"
        const val COLUMN_RATING = "rating"
        const val COLUMN_GENRES = "genres"
        const val COLUMN_YEARS = "years"
        const val COLUMN_IMAGE_URL_LARGE = "image_url_large"
        const val COLUMN_IMAGE_URL_MEDIUM = "image_url_medium"
    }
}