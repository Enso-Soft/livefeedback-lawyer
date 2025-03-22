package com.lafi.lawyer.core.design_system.component.scale_ripple

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View


fun View.setScaleRipple(isRipple: Boolean) {
    if (isRipple) {
        val scaleDown = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(this@setScaleRipple, "scaleX", 0.95f),
                ObjectAnimator.ofFloat(this@setScaleRipple, "scaleY", 0.95f)
            )
            duration = 100
        }

        val scaleUp = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(this@setScaleRipple, "scaleX", 1f),
                ObjectAnimator.ofFloat(this@setScaleRipple, "scaleY", 1f)
            )
            duration = 100
        }

        var isAnimating = false
        var pendingScaleUp = false

        setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!isAnimating) {
                        isAnimating = true
                        scaleDown.start()
                    }
                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (isAnimating) {
                        pendingScaleUp = true
                    } else {
                        scaleUp.start()
                    }
                    view.performClick()
                    true
                }

                MotionEvent.ACTION_CANCEL -> {
                    if (isAnimating) {
                        pendingScaleUp = true
                    } else {
                        scaleUp.start()
                    }
                    true
                }

                else -> false
            }
        }

        scaleDown.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (pendingScaleUp) {
                    pendingScaleUp = false
                    scaleUp.start()
                } else {
                    isAnimating = false
                }
            }
        })

        scaleUp.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isAnimating = false
            }
        })
    } else {
        setOnTouchListener(null)
    }
}