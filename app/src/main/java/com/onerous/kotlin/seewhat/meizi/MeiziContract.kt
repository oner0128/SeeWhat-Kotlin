package com.onerous.kotlin.seewhat.meizi

import com.onerous.kotlin.seewhat.BasePresenter
import com.onerous.kotlin.seewhat.BaseView
import com.onerous.kotlin.seewhat.data.MeiziBean
import com.onerous.kotlin.seewhat.data.MoviesBean

/**
 * Created by oner0128 on 2017/8/12.
 */
interface MeiziContract {
    interface View : BaseView {
        fun showMeizi(meizi: List<MeiziBean.Results>)
    }

    interface Presenter : BasePresenter {
        fun loadMeizis(page:Int)
    }
}