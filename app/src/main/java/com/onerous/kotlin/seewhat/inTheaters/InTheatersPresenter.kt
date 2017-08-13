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
class InTheatersPresenter(val view: InTheatersContract.View) : InTheatersContract.Presenter {

    private val mMoviesRepository = App.mMoviesRepository.InTheatersRepository

    private val mCompositeDisposable = CompositeDisposable()

    override fun subscribe() {
        loadInTheatersMovies(false)
    }

    override fun unsubscribe() {
        mCompositeDisposable.dispose()
    }

    override fun loadInTheatersMovies(forceUpdate: Boolean) {
        if (forceUpdate) {
            Logger.v("$forceUpdate")
            mMoviesRepository.refreshMovies()
        }
        view.showProgressDialog()
        mCompositeDisposable.clear()
        val disposable = mMoviesRepository
                .getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    Logger.v("loadInTheatersMovies-size:${it.size}")
                    view.showMovies(it) }
                        , { error -> view.showError(error.toString()) }
                        , { view.hideProgressDialog() })
        mCompositeDisposable.add(disposable)
    }
}