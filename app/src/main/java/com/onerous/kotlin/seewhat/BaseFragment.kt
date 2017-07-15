package com.onerous.kotlin.seewhat

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by rrr on 2017/7/15.
 */
abstract class BaseFragment():Fragment() {
//    override fun onResume() {
//        super.onResume()
//        presenter.subscribe()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        presenter.unsubscribe()
//    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initView()
//        initListener()
//        initData()
//        init()
    }
//    abstract fun initView()
//    abstract fun initListener()
//    abstract fun initData()
//abstract fun init()
}