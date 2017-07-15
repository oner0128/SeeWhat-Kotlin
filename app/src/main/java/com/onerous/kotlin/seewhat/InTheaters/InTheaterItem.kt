package com.onerous.kotlin.seewhat.InTheaters

import android.content.Intent
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * Created by rrr on 2017/7/15.
 */
class InTheaterItem:ItemViewDelegate<MoviesBean.Subjects> {
    override fun isForViewType(item: MoviesBean.Subjects?, position: Int): Boolean {
        return item is MoviesBean.Subjects
    }

    override fun convert(holder: ViewHolder, t: MoviesBean.Subjects, position: Int) {
        val mContext = holder.convertView.context

        val movieEntity = t as MoviesBean.Subjects
        val imagePosterURL = movieEntity.images.large
        val title = movieEntity.title
        val id = movieEntity.id
        Glide.with(mContext)
                .load(imagePosterURL)
                .placeholder(R.mipmap.ic_launcher)
                //                .error(R.mipmap.ic_launcher_round)
                .override(800, 600)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.getView(R.id.imageView))
        holder.convertView.setOnClickListener(View.OnClickListener {
//            val intent = Intent(mContext, MovieDetailActivity::class.java)
//            intent.putExtra("MovieID", id)
//            intent.putExtra("MovieTitle", title)
//            intent.putExtra("MovieImg", imagePosterURL)
//            mContext.startActivity(intent)
            Log.d("movieTitle:",title)
        })
    }

    override fun getItemViewLayoutId(): Int {
        return R.layout.item_recyclerview_image
    }
}