package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.BasePresenter
import com.onerous.kotlin.seewhat.BaseView
import com.onerous.kotlin.seewhat.data.MoviesBean

/**
 * Created by rrr on 2017/7/15.
 */
interface Top250Contract {
    interface View:BaseView{
        fun showMovies(movies:List<MoviesBean.Subjects>)
        fun showMoreMovies(movies:List<MoviesBean.Subjects>)
        fun showMovieDetail(movie: MoviesBean.Subjects?)
        fun showNoMovies()
    }
    interface Presenter:BasePresenter{
        fun loadTop250Movies(forceUpdate: Boolean,start:Int,count:Int)
//        fun loadMoreTop250Movies(forceUpdate: Boolean,start:Int,count:Int,total:Int)
    }
}