package com.onerous.kotlin.seewhat

/**
 * Created by rrr on 2017/7/15.
 */
interface BaseView<T> {
    fun setPresenter(presenter: T)
    fun showProgressDialog();
    fun hideProgressDialog();
    fun showError(error:String?);
}