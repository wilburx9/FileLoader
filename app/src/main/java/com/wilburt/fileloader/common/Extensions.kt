package com.wilburt.fileloader.common
import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

/**
 * Created by Wilberforce on 2020-02-21 at 17:29.
 */

fun View.fadeIn(duration: Long = 1000): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, View.ALPHA, 0F, 1F).apply {
        addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                this@fadeIn.visibility = View.VISIBLE
                this@fadeIn.isEnabled = true
                removeListener(this)
            }
        })
        setDuration(duration)
        startDelay = startDelay
        start()

    }
}

fun View.fadeOut(duration: Long = 1000): ObjectAnimator {
    return ObjectAnimator.ofFloat(this, View.ALPHA, 1F, 0F).apply {
        addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@fadeOut.visibility = View.GONE
                this@fadeOut.isEnabled = false
                removeListener(this)
            }
        })
        setDuration(duration)
        startDelay = startDelay
        start()

    }
}

/**
 *  Fades in this view and fades out the [otherView] view simultaneously
 *  @param  otherView the view to fade out
 *  @param duration duration of the animation
 *  @param visibility The visibility of [otherView] at the end of the animation
 */
fun View.crossFadeWidth(
    otherView: View,
    duration: Long = 1000,
    visibility: Int = View.GONE
): AnimatorSet {
    val fadeIn = ObjectAnimator.ofFloat(this, View.ALPHA, 0F, 1F)
    val fadeOut = ObjectAnimator.ofFloat(otherView, View.ALPHA, 1F, 0F)

    return AnimatorSet().apply {
        addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                this@crossFadeWidth.isEnabled = true
                otherView.isEnabled = false
                this@crossFadeWidth.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                otherView.visibility = visibility
                this@apply.removeListener(this)
            }
        })
        setDuration(duration)
        startDelay = startDelay
        playTogether(fadeIn, fadeOut)
        start()
    }
}