package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.data.source.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersPresenter(val view: InTheatersContract.View) : InTheatersContract.Presenter {
    init {
        view.setPresenter(this)
    }

    val mMoviesRepository = MoviesRepository.InTheatersDataRepository
    var mFirstLoad = true
    private val mCompositeDisposable = CompositeDisposable()

    override fun subscribe() {
        loadInTheatersMovies(false)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun loadInTheatersMovies(forceUpdate: Boolean) {
        loadInTheatersMovies(forceUpdate || mFirstLoad, true)
        mFirstLoad = false
    }

    private fun loadInTheatersMovies(forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            view.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            mMoviesRepository.refreshMovies()
        }

        mCompositeDisposable.clear()
        val disposable = mMoviesRepository
                .getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> view.showMovies(it) }
                        , { error -> view.showError(error.toString()) }
                        , {
                    view.hideProgressDialog()
                    view.setLoadingIndicator(false)
                }
                        , { view.showProgressDialog() })
        mCompositeDisposable.add(disposable)
    }
}