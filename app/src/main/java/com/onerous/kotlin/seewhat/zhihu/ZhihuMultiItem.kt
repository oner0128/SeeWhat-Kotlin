package com.onerous.kotlin.seewhat.zhihu

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.onerous.kotlin.seewhat.data.ZhihuBeforeNewsBean
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean
import com.orhanobut.logger.Logger

/**
 * Created by rrr on 2017/7/17.
 */
open class ZhihuMultiItem(val Itemtype: Int) : MultiItemEntity {
    constructor(itemType: Int, date: String?) : this(itemType) {
        this.mDate= date
    }
    constructor(itemType: Int, zhihuLatestNewsBean: ZhihuLatestNewsBean) : this(itemType) {
        Logger.v("${zhihuLatestNewsBean.date}")
        this.mZhihuLatestNewsBean= zhihuLatestNewsBean
    }
    constructor(itemType: Int, storie: ZhihuLatestNewsBean.Stories) : this(itemType) {
        this.mLatestNews = storie
    }
    constructor(itemType: Int, storie: ZhihuBeforeNewsBean.Stories) : this(itemType) {
        this.mBeforeNews = storie
    }
     var mDate:String?=null
     var mZhihuLatestNewsBean:ZhihuLatestNewsBean?=null
     var mLatestNews:ZhihuLatestNewsBean.Stories?=null
    var mBeforeNews:ZhihuBeforeNewsBean.Stories?=null
    override fun getItemType(): Int {
        return Itemtype
    }

    companion object {
        val HEADER = 0
        val DATE = 1
        val NEWS = 2
        val BEFORENEWS = 3
    }

}