package com.lafi.lawyer.core.design_system.component.scale_ripple

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Outline
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import com.lafi.lawyer.core.design_system.component.keyboard_sync_layout.KeyboardSyncLayout

@SuppressLint("ClickableViewAccessibility")
fun View.setOnScaleClickListener(
    scale: Int,
    action: (View.() -> Unit)? = null
) {
    // 유효한 스케일 값 계산
    val calculatedScale = when {
        scale < 0 -> 0.9f
        scale > 100 -> 1f
        else -> scale * 0.01f
    }

    // Store reference to outline provider
    val originalOutlineProvider = outlineProvider

    // Create a custom outline provider that will maintain corners during scaling
    if (clipToOutline && originalOutlineProvider != null) {
        val customOutlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                // Call the original provider to get the current outline
                originalOutlineProvider.getOutline(view, outline)
            }
        }

        // Set our custom provider
        outlineProvider = customOutlineProvider
    }

    val scaleDown = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@setOnScaleClickListener, "scaleX", calculatedScale),
            ObjectAnimator.ofFloat(this@setOnScaleClickListener, "scaleY", calculatedScale)
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

@SuppressLint("ClickableViewAccessibility")
fun KeyboardSyncLayout.setOnKeyboardSyncScaleClickListener(
    scale: Int,
    action: (View.() -> Unit)? = null
) {
    // 유효한 스케일 값 계산
    val calculatedScale = when {
        scale < 0 -> 0.9f
        scale > 100 -> 1f
        else -> scale * 0.01f
    }

    // KeyboardSyncLayout 클래스의 currentRadius 필드에 직접 접근할 수 없으므로
    // 리플렉션을 사용하여 현재 radius 값을 확인
    fun getCurrentRadius(): Float {
        try {
            val radiusField = KeyboardSyncLayout::class.java.getDeclaredField("currentRadius")
            radiusField.isAccessible = true
            return radiusField.getFloat(this@setOnKeyboardSyncScaleClickListener)
        } catch (e: Exception) {
            // 리플렉션 실패 시 fallback으로 0 반환
            return 0f
        }
    }

    // radius가 0보다 큰지 확인
    fun hasEffectiveRadius(): Boolean {
        val radius = getCurrentRadius()
        return radius > 0.1f  // 약간의 threshold를 두어 미세한 값은 무시
    }

    val scaleDown = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@setOnKeyboardSyncScaleClickListener, "scaleX", calculatedScale),
            ObjectAnimator.ofFloat(this@setOnKeyboardSyncScaleClickListener, "scaleY", calculatedScale)
        )
        duration = 90
    }

    val scaleUp = AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@setOnKeyboardSyncScaleClickListener, "scaleX", 1f),
            ObjectAnimator.ofFloat(this@setOnKeyboardSyncScaleClickListener, "scaleY", 1f)
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
        // 매 터치 이벤트마다 현재 radius 확인
        val hasRadius = hasEffectiveRadius()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isValidClick = true
                pendingScaleUp = false
                view.isPressed = true

                // radius가 설정된 경우에만 스케일 애니메이션 실행
                if (hasRadius) {
                    scaleDown.cancel()
                    scaleDown.start()
                }
                true
            }

            MotionEvent.ACTION_UP -> {
                view.isPressed = false
                if (event.x < 0 || event.x > view.width || event.y < 0 || event.y > view.height) {
                    isValidClick = false
                }

                if (hasRadius) {
                    if (isAnimating) {
                        pendingScaleUp = true
                    } else {
                        startScaleUpIfNeeded()
                    }
                } else if (isValidClick) {
                    // radius가 없는 경우 액션 즉시 실행
                    action?.let { it() } ?: run { performClick() }
                }
                true
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.x < 0 || event.x > view.width || event.y < 0 || event.y > view.height) {
                    view.isPressed = false
                    isValidClick = false
                    if (hasRadius) {
                        if (isAnimating) {
                            pendingScaleUp = true
                        } else {
                            startScaleUpIfNeeded()
                        }
                    }
                }
                true
            }

            MotionEvent.ACTION_CANCEL -> {
                view.isPressed = false
                isValidClick = false
                if (hasRadius) {
                    if (isAnimating) {
                        pendingScaleUp = true
                    } else {
                        startScaleUpIfNeeded()
                    }
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