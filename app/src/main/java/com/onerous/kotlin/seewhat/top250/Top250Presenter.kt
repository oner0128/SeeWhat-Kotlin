package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.data.source.MoviesRepository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/15.
 */
class Top250Presenter(val view: Top250Contract.View) : Top250Contract.Presenter {

    private val mMoviesRepository = App.mMoviesRepository.Top250Repository
    private val mCompositeDisposable = CompositeDisposable()

    override fun loadTop250Movies(forceUpdate: Boolean, start: Int, count: Int) {

        if (forceUpdate) mMoviesRepository.refreshMovies()

        mCompositeDisposable.clear()

        if (start == 0) view.showProgressDialog()
        val disposable = mMoviesRepository
                .getMovies(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { it ->
                            Logger.v("loadTop250Movies${it.size}")
                            if (start == 0) view.showMovies(it)
                            else view.showMoreMovies(it)
                        }
                        , { error -> view.showError(error.toString()) }
                        , { view.hideProgressDialog() })

        mCompositeDisposable.add(disposable)
    }

    override fun subscribe() {
        loadTop250Movies(false, 0, 10)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}