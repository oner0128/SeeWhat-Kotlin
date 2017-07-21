package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.api.ApiService
import com.onerous.kotlin.seewhat.data.source.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersPresenter(val fragment: InTheatersContract.View) : InTheatersContract.Presenter {

    val mMoviesRepository=MoviesRepository
    var mFirstLoad = true
    private val mSubscriptions=CompositeDisposable()

    override fun subscribe() {
        getInTheatersMovies(false)
    }

    override fun unsubscribe() {
        mSubscriptions.clear()
    }

    override fun getInTheatersMovies() {
        fragment.showProgressDialog()
        val disposable = ApiService.douBanService
                .getInTheatersMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> fragment.showMovies(it) },
                        { error -> fragment.showError(error.message) },
                        { fragment.hideProgressDialog() })
        mSubscriptions.add(disposable)
    }
    override fun getInTheatersMovies(forceUpdate: Boolean) {
        getInTheatersMovies(forceUpdate || mFirstLoad, true)
        mFirstLoad = false
    }

    private fun getInTheatersMovies(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            fragment.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            mMoviesRepository.refreshMovies()
        }

        mSubscriptions.clear()
//        val subscription = mMoviesRepository
//
//        mSubscriptions.add(subscription)
    }
}