package com.onerous.kotlin.seewhat.inTheaters

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.onerous.kotlin.seewhat.App
import com.onerous.kotlin.seewhat.R
import com.onerous.kotlin.seewhat.data.MoviesBean.Subjects
import com.zhy.adapter.recyclerview.base.ItemViewDelegate
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * Created by rrr on 2017/7/17.
 */
class Top250Item : ItemViewDelegate<Subjects> {
    override fun isForViewType(item: Subjects?, position: Int): Boolean {
        return item is Subjects
    }

    override fun convert(holder: ViewHolder, t: Subjects, position: Int) {
        val mContext = holder.convertView.context

        val movieEntity = t
        val imagePosterURL = movieEntity.images.large
        val title = movieEntity.title
        val id = movieEntity.id
        Glide.with(mContext)
                .load(imagePosterURL)
                .placeholder(R.mipmap.ic_launcher)
                //                .error(R.mipmap.ic_launcher_round)
                .override(App.imageSize[0], App.imageSize[1])
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .centerCrop()
                .into(holder.getView(R.id.imageView))
        holder.convertView.setOnClickListener(View.OnClickListener {
            //            val intent = Intent(mContext, MovieDetailActivity::class.java)
//            intent.putExtra("MovieID", id)
//            intent.putExtra("MovieTitle", title)
//            intent.putExtra("MovieImg", imagePosterURL)
//            mContext.startActivity(intent)
            Log.d("movieTitle:", title)
        })
    }

    override fun getItemViewLayoutId(): Int =R.layout.item_recyclerview_top250
}