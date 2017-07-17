package com.onerous.kotlin.seewhat.zhihu.item

import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.util.DateUtil
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder
import java.util.*

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuHeaderTitleItemDelegate :ItemViewDelegate<ZhihuItem>{
    override fun convert(holder: ViewHolder, t: ZhihuItem?, position: Int) {
        if (position == 1) {
            holder.setText(R.id.tv_zhihu_header, "今日热闻")
        } else {
            holder.setText(R.id.tv_zhihu_header, (t as ZhihuHeaderTitleItem).formatDate)
        }
    }

    override fun getItemViewLayoutId(): Int= R.layout.item_zhihu_header_title

    override fun isForViewType(item: ZhihuItem?, position: Int): Boolean =item is ZhihuHeaderTitleItem
}
class ZhihuHeaderTitleItem(strDate: String) : ZhihuItem {
    var formatDate: String = DateUtil.formatStrDate(strDate,"yyyyMMdd")
}