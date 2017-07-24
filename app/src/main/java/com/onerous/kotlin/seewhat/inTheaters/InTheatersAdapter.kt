package com.onerous.kotlin.seewhat.inTheaters

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersAdapter(layoutResId: Int, data: MutableList<MoviesBean.Subjects>?) : BaseQuickAdapter<MoviesBean.Subjects, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MoviesBean.Subjects) {
        Glide.with(mContext)
                .load(item.images.large)
                .override(App.imageSize[0], App.imageSize[1])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(helper.getView(R.id.imageView))
    }
}