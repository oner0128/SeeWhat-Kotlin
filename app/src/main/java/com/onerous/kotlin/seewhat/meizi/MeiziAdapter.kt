package com.onerous.kotlin.seewhat.meizi

import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MeiziBean
import com.onerous.kotlin.seewhat.data.MoviesBean

/**
 * Created by oner0128 on 2017/8/12.
 */
class MeiziAdapter(layoutResId: Int, data: MutableList<MeiziBean.Results>?) : BaseQuickAdapter<MeiziBean.Results, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MeiziBean.Results) {
        Glide.with(mContext)
                .load(item.url)
                .override(App.imageSize[0], App.imageSize[1])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(helper.getView(R.id.iv_item_meizi))
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            helper.getView<ImageView>(R.id.iv_item_meizi).transitionName=item._id
//        }
    }
}