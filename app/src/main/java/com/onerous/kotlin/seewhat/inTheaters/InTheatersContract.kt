package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.BasePresenter
import com.onerous.kotlin.seewhat.BaseView
import com.onerous.kotlin.seewhat.data.MoviesBean

/**
 * Created by rrr on 2017/7/15.
 */
interface InTheatersContract {
    interface View : BaseView {
        fun showMovies(movies: List<MoviesBean.Subjects>)
        fun showMovieDetail(movie: MoviesBean.Subjects?)
    }

    interface Presenter : BasePresenter {
        fun loadInTheatersMovies(forceUpdate: Boolean)
    }
}