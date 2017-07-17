package com.onerous.kotlin.seewhat.zhihu

import com.onerous.kotlin.seewhat.api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuPresenter(val fragment: ZhihuContract.View) :ZhihuContract.Presenter {
    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unsubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getZhihuLastestNews() {
        fragment.showProgressDialog()
        val disposable=ApiService.zhiHuService.getZhihuLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> fragment.showLastestNews(it) },
                        { error -> fragment.showError(error.message) },
                        { fragment.hideProgressDialog() })
        CompositeDisposable().add(disposable)
    }
}