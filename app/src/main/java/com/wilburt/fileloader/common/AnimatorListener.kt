package com.wilburt.fileloader.common

import android.animation.Animator


/**
 * Created by Wilberforce on 2020-02-21 at 17:30.
 */


interface AnimatorListener: Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {}

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationStart(animation: Animator?) {}
}