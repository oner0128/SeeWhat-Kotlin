package com.onerous.kotlin.seewhat.InTheaters

import android.content.Context
import com.onerous.kotlin.seewhat.data.Subjects
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter

/**
 * Created by rrr on 2017/7/15.
 */
class InTheatersAdapter(context: Context, datas: ArrayList<Subjects>) : MultiItemTypeAdapter<Subjects>(context, datas) {
    init {
        addItemViewDelegate(InTheaterItem())
    }
}