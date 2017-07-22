package com.onerous.kotlin.seewhat.inTheaters

import com.onerous.kotlin.seewhat.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/15.
 */
class Top250Presenter(val view: Top250Contract.View) : Top250Contract.Presenter {
    init {
        view.setPresenter(this)
    }
    val compositeDisposable=CompositeDisposable()
    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTop250Movies(start: Int, count: Int) {
        view.showProgressDialog()
        val disposable = ApiService.douBanService
                .getTop250Movies(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> view.showMovies(it) },
                        { error -> view.showError(error.message) },
                        { view.hideProgressDialog() })
        compositeDisposable.add(disposable)
    }
    override fun getMoreTop250Movies(start: Int, count: Int) {
//        view.showProgressDialog()
        val disposable = ApiService.douBanService
                .getTop250Movies(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> view.showMoreMovies(it) }
                        , { error -> view.showError(error.message) }
//                        , { view.hideProgressDialog() }
                )

        compositeDisposable.add(disposable)
    }
}