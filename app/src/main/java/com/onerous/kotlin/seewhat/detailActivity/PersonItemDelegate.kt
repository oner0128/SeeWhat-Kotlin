package com.onerous.kotlin.seewhat.detailActivity

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.PersonBean
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder


/**
 * Created by rrr on 2017/7/19.
 */

class PersonItemDelegate : ItemViewDelegate<PersonBean> {
    override fun getItemViewLayoutId(): Int = R.layout.item_recyclerview_person

    override fun isForViewType(personBean: PersonBean, i: Int): Boolean = personBean is PersonBean

    override fun convert(viewHolder: ViewHolder, personBean: PersonBean, i: Int) {
        val mContext = viewHolder.convertView.context
        Glide.with(mContext)
                .load(personBean.getImgUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.getView(R.id.imageV_post))

        viewHolder.setText(R.id.tv_name, personBean.getPersonName())
        viewHolder.setText(R.id.tv_job, personBean.getJob())
        viewHolder.convertView.setOnClickListener( {
            Logger.v(personBean.getPersonName())
            showCustomTabs("https://movie.douban.com/celebrity/${personBean.getPersonId()}/mobile", mContext)
        })
    }
}
