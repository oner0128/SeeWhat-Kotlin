package com.onerous.kotlin.seewhat.util

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * Created by oner0128 on 2017/8/11.
 */

interface HideOrShowListener {
    fun hide(view: View)
    fun show(view: View)
}
enum class HideOrShowState {
    SHOW,
    HIDE,
    isShowing,
    isHiding
}
abstract class HideOrShowBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {
    
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return (axes== ViewCompat.SCROLL_AXIS_VERTICAL)//只监听垂直滑动
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (Math.abs(dy)>2){
            if (dy>0)//向下滑
                scrollDown(child)
            else scrollUp(child)
        }
    }
    
    abstract fun scrollDown(view: View)
    abstract fun scrollUp(view: View)
    
    companion object {
        fun from(view: View): HideOrShowBehavior {
            val layoutParams=view.layoutParams
            if (!(layoutParams is CoordinatorLayout.LayoutParams))
                throw IllegalArgumentException("传入的view不是CoordinatorLayout的子view")
            val behavior=layoutParams.behavior
            if (!(behavior is CoordinatorLayout.Behavior))
                throw IllegalArgumentException("传入的behavior不是CoordinatorLayout的behavior")
            return behavior as HideOrShowBehavior
        }
    }
}
