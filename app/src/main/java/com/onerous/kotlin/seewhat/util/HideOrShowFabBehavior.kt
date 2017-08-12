package com.onerous.kotlin.seewhat.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by oner0128 on 2017/8/12.
 */
class HideOrShowFabBehavior(context: Context?, attrs: AttributeSet?) : HideOrShowBehavior(context, attrs), HideOrShowListener {
    var mAnimatorState = HideOrShowState.SHOW
    override fun hide(view: View) {

        val animator = view.animate()
                .translationY(view.height.toFloat() + view.bottom.toFloat())
//                .setInterpolator(mInterpolator)
                .setDuration(800)

        animator.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
//                com.orhanobut.logger.Logger.v("animator end")
                view.visibility = View.INVISIBLE
            }
        })

        animator.start()
    }

    override fun show(view: View) {

        val animator = view.animate()
                .translationY(0f)
//                .setInterpolator(mInterpolator)
                .setDuration(800)

        animator.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.visibility = View.VISIBLE
            }
        })

        animator.start()
    }

    override fun scrollDown(view: View) {
        if (mAnimatorState == HideOrShowState.SHOW && view.visibility == View.VISIBLE) {
            hide(view)
            mAnimatorState = HideOrShowState.HIDE
        }
    }

    override fun scrollUp(view: View) {
        if (mAnimatorState == HideOrShowState.HIDE && view.visibility == View.INVISIBLE) {
            show(view)
            mAnimatorState = HideOrShowState.SHOW
        }
    }
}