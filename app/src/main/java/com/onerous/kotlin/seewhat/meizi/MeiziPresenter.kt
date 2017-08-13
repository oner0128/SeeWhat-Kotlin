package com.onerous.kotlin.seewhat.meizi

import com.onerous.kotlin.seewhat.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by oner0128 on 2017/8/12.
 */
class MeiziPresenter(val view: MeiziContract.View) : MeiziContract.Presenter {
    override fun loadMeizis(page: Int) {
        view.showProgressDialog()
        val disposable = ApiService.gankService.getMeizi(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> view.showMeizi(it.results) },
                        { error -> view.showError(error.message) },
                        { view.hideProgressDialog() })
        mCompositeDisposable.add(disposable)
    }


    private val mCompositeDisposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.dispose()
    }
}