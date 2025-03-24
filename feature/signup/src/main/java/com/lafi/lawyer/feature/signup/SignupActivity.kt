package com.lafi.lawyer.feature.signup

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : BaseActivity<FeatureSignupActivitySignupBinding>(FeatureSignupActivitySignupBinding::inflate) {
    private val vm by viewModels<SignupViewModel>()

    private val textInputViews: List<View> by lazy {
        listOf(
            binding.tiName,
            binding.tiPhoneNumber,
            binding.tiEmail
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        initListener()
    }

    private fun setupUi() {
        if (isFirstCreate) {
            lifecycleScope.launch(Dispatchers.Main.immediate) {
                textInputViews.forEach { view ->
                    delay(300L)
                    view.slideInFromRight(500L)
                }
                delay(500L)
                textInputViews.firstOrNull()?.requestFocus()
            }
        } else {
            textInputViews.forEach { it.visibility = View.VISIBLE }
        }
    }

    private fun initListener() {
        with(binding) {
            topBar.setOnCloseClickListener { finish() }
        }
    }
}

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