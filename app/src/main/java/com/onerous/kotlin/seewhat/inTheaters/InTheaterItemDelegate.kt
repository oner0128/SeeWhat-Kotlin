package com.onerous.kotlin.seewhat.inTheaters

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean.Subjects
import com.onerous.kotlin.seewhat.detailActivity.MovieDetailActivity
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * Created by rrr on 2017/7/15.
 */
class InTheaterItemDelegate : ItemViewDelegate<Subjects> {
    override fun isForViewType(item: Subjects?, position: Int): Boolean {
        return item is Subjects
    }

    override fun convert(holder: ViewHolder, movieEntity: Subjects, position: Int) {
        val mContext = holder.convertView.context
        Glide.with(mContext)
                .load(movieEntity.images.large)
                .placeholder(R.mipmap.ic_launcher)
                //                .error(R.mipmap.ic_launcher_round)
                .override(App.imageSize[0], App.imageSize[1])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.getView(R.id.imageView))
        holder.convertView.setOnClickListener({
            Logger.v("movieTitle:", movieEntity.images.large)
            val intent = Intent(mContext, MovieDetailActivity::class.java)
            intent.putExtra("MovieId", movieEntity.id)
            intent.putExtra("MovieTitle", movieEntity.title)
            intent.putExtra("MovieImg", movieEntity.images.large)
            mContext.startActivity(intent)
        })
    }

    override fun getItemViewLayoutId(): Int {
        return R.layout.item_recyclerview_intheaters
    }
}