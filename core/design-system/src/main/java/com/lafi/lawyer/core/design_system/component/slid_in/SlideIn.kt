package com.lafi.lawyer.core.design_system.component.slid_in

import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

fun View.slideInFromRight(duration: Long = 500L) {
    // 초기에는 뷰를 숨김
    this.visibility = View.INVISIBLE

    // 핸들러를 사용하여 지연 후 애니메이션 시작
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
        with(this@slideInFromRight) {
            visibility = View.VISIBLE

            // 애니메이션 설정
            this.translationX = (width/2).toFloat() // 오른쪽에서 시작
            this.alpha = 0f // 완전히 투명에서 시작

            this.animate()
                .translationX(0f) // 원래 위치로
                .alpha(1f) // 완전히 불투명하게
                .setDuration(duration)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }
}