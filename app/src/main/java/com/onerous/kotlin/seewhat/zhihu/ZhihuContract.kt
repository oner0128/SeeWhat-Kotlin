package com.onerous.kotlin.seewhat.zhihu

import com.onerous.kotlin.seewhat.BasePresenter
import com.onerous.kotlin.seewhat.BaseView
import com.onerous.kotlin.seewhat.data.ZhihuBeforeNewsBean
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean

/**
 * Created by rrr on 2017/7/17.
 */
interface ZhihuContract {
    interface View : BaseView {
        fun showLastestNews(zhihuLatestNewsBean: ZhihuLatestNewsBean)
        fun showBeforeNews(zhihuBeforeNewsBean: ZhihuBeforeNewsBean)
    }

    interface Presenter : BasePresenter {
        fun getZhihuLastestNews()
        fun getZhihuBeforeNews(date:String)
    }
}