package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersPresenter(val fragment: InTheatersContract.View) : InTheatersContract.Presenter {

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

        CompositeDisposable().add(disposable)
    }
}