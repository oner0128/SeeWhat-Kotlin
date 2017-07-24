package com.onerous.kotlin.seewhat.detailActivity

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.PersonBean


/**
 * Created by rrr on 2017/7/19.
 */

class PersonItemAdapter(layoutResId: Int, data: MutableList<PersonBean>?) : BaseQuickAdapter<PersonBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, personBean: PersonBean) {
        Glide.with(mContext)
                .load(personBean.getImgUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(helper.getView(R.id.imageV_post))

        helper.setText(R.id.tv_name, personBean.getPersonName())
        helper.setText(R.id.tv_job, personBean.getJob())

    }
}
