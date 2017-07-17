package com.onerous.kotlin.seewhat.zhihu.item

import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * Created by rrr on 2017/7/17.
 */
class ZhihuHeaderBannerItemDelegate : ItemViewDelegate<ZhihuItem> {
    override fun convert(holder: ViewHolder, t: ZhihuItem?, position: Int) {
        val mContext = holder.getConvertView().getContext()
        val banner: BGABanner = holder.getView(R.id.banner_zhihu_header)
        val item = t as ZhihuLatestNewsBean
        val topStories = item.top_stories.toList()
        val images = ArrayList<String>()
        val titles = ArrayList<String>()
        topStories.forEach({ it ->
            images.add(it.image)
            titles.add(it.title)
        })
        banner.setAdapter(BGABanner.Adapter<ImageView, String> {
            banner, itemView, model, position ->
            Glide.with(mContext)
                    .load(model)
                    .centerCrop()
                    .dontAnimate()
                    .into(itemView)
        })
        banner.setDelegate(BGABanner.Delegate<ImageView, String> {
            banner, itemView, model, position ->
            Logger.d(topStories.get(position).title) })
        banner.setData(images, titles)
    }

    override fun getItemViewLayoutId(): Int = R.layout.item_zhihu_header_banner

    override fun isForViewType(item: ZhihuItem?, position: Int): Boolean = item is ZhihuLatestNewsBean
}