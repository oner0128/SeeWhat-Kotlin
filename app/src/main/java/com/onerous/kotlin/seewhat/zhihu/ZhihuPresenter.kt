package com.onerous.kotlin.seewhat.zhihu

import com.onerous.kotlin.seewhat.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuPresenter(val view: ZhihuContract.View) : ZhihuContract.Presenter {

    override fun getZhihuBeforeNews(date: String) {
        val disposable = ApiService.zhiHuService.getZhihuBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> view.showBeforeNews(it) },
                        { error -> view.showError(error.message) })
        CompositeDisposable().add(disposable)
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getZhihuLastestNews() {
        view.showProgressDialog()
        val disposable = ApiService.zhiHuService.getZhihuLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> view.showLastestNews(it) },
                        { error -> view.showError(error.message) },
                        { view.hideProgressDialog() })
        CompositeDisposable().add(disposable)
    }
}