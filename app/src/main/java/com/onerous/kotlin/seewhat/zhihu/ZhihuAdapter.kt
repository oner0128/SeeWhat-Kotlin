package com.onerous.kotlin.seewhat.zhihu

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.ZhihuLatestNewsBean
import com.onerous.kotlin.seewhat.detailActivity.ZhihuStoryDetailActivity
import com.onerous.kotlin.seewhat.util.DateUtil
import com.orhanobut.logger.Logger

/**
 * Created by rrr on 2017/7/24.
 */
class ZhihuAdapter(data: MutableList<ZhihuMultiItem>?) : BaseMultiItemQuickAdapter<ZhihuMultiItem, BaseViewHolder>(data) {
    init {
        addItemType(ZhihuMultiItem.HEADER, R.layout.item_zhihu_header_banner)
        addItemType(ZhihuMultiItem.DATE, R.layout.item_zhihu_header_date)
        addItemType(ZhihuMultiItem.NEWS, R.layout.item_zhihu_stories_list)
        addItemType(ZhihuMultiItem.BEFORENEWS, R.layout.item_zhihu_stories_list)
    }

    override fun convert(helper: BaseViewHolder, item: ZhihuMultiItem) {
        when (helper.itemViewType) {
            ZhihuMultiItem.HEADER -> {
                val banner: BGABanner = helper.getView(R.id.banner_zhihu_header)
                val header=item.mZhihuLatestNewsBean
                if (header==null)Logger.v("header==null")
                val topStories = header!!.top_stories.toList()
                val images = ArrayList<String>()
                val titles = ArrayList<String>()
                topStories.forEach { it ->
                    images.add(it.image)
                    titles.add(it.title)
                }
                banner.setAdapter {
                    banner, itemView, model, position ->
                    Glide.with(mContext)
                            .load(model)
                            .centerCrop()
                            .dontAnimate()
                            .into(itemView as ImageView)
                }
                banner.setDelegate {
                    banner, itemView, model, position ->
                    Logger.d(topStories.get(position).title)
                    val intent = Intent(mContext, ZhihuStoryDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("storyId", topStories.get(position).id)
                    bundle.putString("storyTitle", topStories.get(position).title)
                    intent.putExtra("story", bundle)
                    mContext.startActivity(intent)
                }
                banner.setData(images, titles)
            }

            ZhihuMultiItem.DATE -> {
//                Logger.v("${item.mDate}")
                if (helper.layoutPosition == 1) helper.setText(R.id.tv_zhihu_date, "今日热闻")
                else helper.setText(R.id.tv_zhihu_date, DateUtil.formatStrDate(item.mDate!!, "yyyyMMdd"))
            }

            ZhihuMultiItem.NEWS -> {
                val storiesEntity = item.mLatestNews!!

                helper.setText(R.id.story_title_tv, storiesEntity.title)

                if (storiesEntity.images != null) {
                    Glide.with(mContext).load(storiesEntity.images.first()).into(helper.getView(R.id.story_iv))
                    helper.setVisible(R.id.multi_pic_iv, storiesEntity.images.size > 1)
                    helper.setVisible(R.id.story_frame_iv, true)
                } else helper.setVisible(R.id.story_frame_iv, false)
            }
            ZhihuMultiItem.BEFORENEWS -> {
                val storiesEntity = item.mBeforeNews!!

                helper.setText(R.id.story_title_tv, storiesEntity.title)

                if (storiesEntity.images != null) {
                    val stroyImg: ImageView = helper.getView(R.id.story_iv)
                    //Glide.with(helper.getConvertView().getContext()).load(storiesEntity.getImages().get(0)).into(stroyImg);
                    Glide.with(mContext).load(storiesEntity.images.first()).into(stroyImg)
                    helper.setVisible(R.id.multi_pic_iv, storiesEntity.images.size > 1)
                    helper.setVisible(R.id.story_frame_iv, true)
                } else helper.setVisible(R.id.story_frame_iv, false)
            }
        }
    }
}