package com.onerous.kotlin.seewhat.zhihu

import com.onerous.kotlin.seewhat.BasePresenter
import com.onerous.kotlin.seewhat.BaseView
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean

/**
 * Created by rrr on 2017/7/17.
 */
interface ZhihuContract {
    interface View : BaseView<Presenter> {
        fun showLastestNews(zhihuLatestNewsBean: ZhihuLatestNewsBean)
    }

    interface Presenter : BasePresenter {
        fun getZhihuLastestNews()
    }
}