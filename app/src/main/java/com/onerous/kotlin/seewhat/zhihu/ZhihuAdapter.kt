package com.onerous.kotlin.seewhat.zhihu

import android.content.Context
import com.onerous.kotlin.seewhat.zhihu.item.*
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuAdapter(context: Context?, datas: MutableList<ZhihuItem>?) : MultiItemTypeAdapter<ZhihuItem>(context, datas) {
    init {
        addItemViewDelegate(ZhihuHeaderBannerItemDelegate())
        addItemViewDelegate(ZhihuHeaderTitleItemDelegate())
        addItemViewDelegate(ZhihuStoriesItemDelegate())
        addItemViewDelegate(ZhihuBeforeStoriesItemDelegate())
    }
}