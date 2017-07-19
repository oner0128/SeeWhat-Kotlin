package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.BasePresenter
import com.onerous.kotlin.seewhat.BaseView
import com.onerous.kotlin.seewhat.data.MoviesBean

/**
 * Created by rrr on 2017/7/15.
 */
interface Top250Contract {
    interface View:BaseView<Presenter>{
        fun showMovies(moviesBean: MoviesBean)
        fun showMoreMovies(moviesBean: MoviesBean)
    }
    interface Presenter:BasePresenter{
        fun getTop250Movies(start:Int,count:Int)
        fun getMoreTop250Movies(start:Int,count:Int)
    }
}