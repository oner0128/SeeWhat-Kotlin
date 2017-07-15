package com.onerous.kotlin.seewhat.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by rrr on 2017/7/15.
 */
fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment?, frameId: Int) {
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(frameId, fragment)
    transaction.commit()
}