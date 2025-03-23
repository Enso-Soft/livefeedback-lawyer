package com.lafi.lawyer.core.design_system.component.scale_ripple

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
fun View.setOnScaleClickListener(
    scale: Int,
    action: (View.() -> Unit)? = null
) {
    // 유효한 스케일 값 계산
    val calculatorScale = when {
        scale < 0 -> 0.9f
        scale > 100 -> 1f
        else -> scale * 0.01f
    }

    val scaleDown = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@setOnScaleClickListener, "scaleX", calculatorScale),
            ObjectAnimator.ofFloat(this@setOnScaleClickListener, "scaleY", calculatorScale)
        )
        duration = 90
    }

    val scaleUp = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@setOnScaleClickListener, "scaleX", 1f),
            ObjectAnimator.ofFloat(this@setOnScaleClickListener, "scaleY", 1f)
        )
        duration = 90
    }

    var isAnimating = false
    var pendingScaleUp = false
    var isValidClick = false

    fun startScaleUpIfNeeded() {
        if (scaleX != 1f || scaleY != 1f) {
            scaleUp.start()
        }
    }

    setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isValidClick = true
                pendingScaleUp = false
                view.isPressed = true
                scaleDown.cancel()
                scaleDown.start()
                true
            }

            MotionEvent.ACTION_UP -> {
                view.isPressed = false
                if (event.x < 0 || event.x > view.width || event.y < 0 || event.y > view.height) {
                    isValidClick = false
                }

                if (isAnimating) {
                    pendingScaleUp = true
                } else {
                    startScaleUpIfNeeded()
                }
                true
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.x < 0 || event.x > view.width || event.y < 0 || event.y > view.height) {
                    view.isPressed = false
                    isValidClick = false
                    if (isAnimating) {
                        pendingScaleUp = true
                    } else {
                        startScaleUpIfNeeded()
                    }
                }
                true
            }

            MotionEvent.ACTION_CANCEL -> {
                view.isPressed = false
                isValidClick = false
                if (isAnimating) {
                    pendingScaleUp = true
                } else {
                    startScaleUpIfNeeded()
                }
                true
            }

            else -> false
        }
    }

    scaleDown.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            isAnimating = true
        }

        override fun onAnimationEnd(animation: Animator) {
            if (pendingScaleUp) {
                pendingScaleUp = false
                startScaleUpIfNeeded()
            } else {
                isAnimating = false
            }
        }
    })

    scaleUp.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            isAnimating = true
        }

        override fun onAnimationEnd(animation: Animator) {
            isAnimating = false
            if (isValidClick) {
                action?.let { it() } ?: run { performClick() }
            }
        }
    })

    // 뷰 분리 시 애니메이션 정리
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {}

        override fun onViewDetachedFromWindow(v: View) {
            scaleDown.cancel()
            scaleUp.cancel()
            setOnTouchListener(null)
            removeOnAttachStateChangeListener(this)
        }
    })
}