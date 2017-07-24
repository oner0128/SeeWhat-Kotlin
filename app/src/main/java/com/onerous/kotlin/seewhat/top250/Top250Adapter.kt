package com.onerous.kotlin.seewhat.top250

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.onerous.kotlin.seewhat.util.formatCastsToString
import com.onerous.kotlin.seewhat.util.formatListToString

/**
 * Created by rrr on 2017/7/22.
 */
class Top250Adapter(layoutResId: Int,  data: MutableList<MoviesBean.Subjects>) : BaseQuickAdapter<MoviesBean.Subjects, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MoviesBean.Subjects) {
        Glide.with(mContext)
                .load(item.images.medium)
                .override(App.imageSize[0], App.imageSize[1])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(helper.getView(R.id.imageView))

        helper.setText(R.id.tv_title, "${(helper.adapterPosition + 1)}.${item.title}")

        helper.setText(R.id.tv_rating, "${item.rating.average}/10.0")

        helper.setText(R.id.tv_casts, "主演:${formatCastsToString(item.casts)}")

        helper.setText(R.id.tv_director, "导演:${formatCastsToString(item.directors)}")

        helper.setText(R.id.tv_years, "年份:${item.year}")

        helper.setText(R.id.tv_genres, "类型:${formatListToString(item.genres)}")
    }
}