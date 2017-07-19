package com.onerous.kotlin.seewhat.inTheaters

import android.content.Context
import com.onerous.kotlin.seewhat.data.MoviesBean
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter

/**
 * Created by rrr on 2017/7/15.
 */
class Top250Adapter(context: Context, datas: ArrayList<MoviesBean.Subjects>) : MultiItemTypeAdapter<MoviesBean.Subjects>(context, datas) {
    init {
        addItemViewDelegate(Top250ItemDelegate())
    }
}