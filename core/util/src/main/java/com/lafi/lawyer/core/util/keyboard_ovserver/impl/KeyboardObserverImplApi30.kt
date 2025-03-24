package com.lafi.lawyer.core.util.keyboard_ovserver.impl

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import com.lafi.lawyer.core.util.keyboard_ovserver.KeyboardObserver
import com.lafi.lawyer.core.util.keyboard_ovserver.KeyboardVisibilityListener

internal class KeyboardObserverImplApi30(
    private val rootView: View
) : KeyboardObserver {
    private var listener: KeyboardVisibilityListener? = null

    // 마지막에 완전 노출되었던 키보드 높이 (사라질 때 기준값으로 사용)
    private var previousFinalKeyboardHeight: Int = 1028

    init {
        registerWindowInsetsAnimationCallback()
        rootView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {}

            override fun onViewDetachedFromWindow(v: View) {
                ViewCompat.setWindowInsetsAnimationCallback(rootView, null)
                listener = null
            }
        })
    }

    private fun registerWindowInsetsAnimationCallback() {
        ViewCompat.setWindowInsetsAnimationCallback(rootView, object : WindowInsetsAnimationCompat.Callback(
            DISPATCH_MODE_STOP
        ) {

            override fun onStart(
                animation: WindowInsetsAnimationCompat,
                bounds: WindowInsetsAnimationCompat.BoundsCompat
            ): WindowInsetsAnimationCompat.BoundsCompat {
                return bounds
            }

            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                // 현재 키보드 높이 (IME 영역)
                val currentKeyboardHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

                // 현재 애니메이션의 목표 높이를 기준으로 진행률 계산
                val normalizedPercent = if (previousFinalKeyboardHeight > 0) {
                    currentKeyboardHeight.toFloat() / previousFinalKeyboardHeight.toFloat()
                } else {
                    0f
                }
                listener?.onKeyboardVisibilityChanged(
                    currentKeyboardHeight > 0,
                    previousFinalKeyboardHeight,
                    normalizedPercent.coerceIn(0f, 1f)
                )
                return insets
            }

            override fun onEnd(animation: WindowInsetsAnimationCompat) {
                // 애니메이션 종료 시 최종 키보드 높이 얻기
                val insets = ViewCompat.getRootWindowInsets(rootView)
                val finalKeyboardHeight = insets?.getInsets(WindowInsetsCompat.Type.ime())?.bottom ?: 0
                // 키보드가 남아 있다면(나타난 상태) 그 높이를 저장
                if (finalKeyboardHeight > 0) {
                    previousFinalKeyboardHeight = finalKeyboardHeight
                }
                // 애니메이션 종료 후 최종 상태 콜백:
                // 키보드가 보이면 1.0, 아니면 0.0
                val finalProgress = if (finalKeyboardHeight > 0) 1f else 0f
                listener?.onKeyboardVisibilityChanged(finalKeyboardHeight > 0, previousFinalKeyboardHeight, finalProgress)
            }
        })
    }

    override fun setListener(listener: KeyboardVisibilityListener) {
        this.listener = listener
    }

    override fun setListener(listener: (visible: Boolean, keyboardHeight: Int, percent: Float) -> Unit) {
        this.setListener(object : KeyboardVisibilityListener {
            override fun onKeyboardVisibilityChanged(visible: Boolean, keyboardHeight: Int, percent: Float) {
                listener.invoke(visible, keyboardHeight, percent)
            }
        })
    }
}