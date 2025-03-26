package com.lafi.lawyer.feature.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lafi.lawyer.core.design_system.R
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.core.design_system.component.scale_ripple.setOnKeyboardSyncScaleClickListener
import com.lafi.lawyer.core.design_system.component.slid_in.slideInFromRight
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

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.none_transition, R.anim.exit_right_transition)
    }

    private fun setupUi() {
        overridePendingTransition(R.anim.enter_left_transition, R.anim.none_transition)
        if (isFirstCreate) {
            lifecycleScope.launch(Dispatchers.Main.immediate) {
                textInputViews.forEach { view ->
                    delay(100L)
                    view.slideInFromRight(300L)
                }
                delay(300L)
                textInputViews.firstOrNull()?.requestFocus()
            }
        } else {
            textInputViews.forEach { it.visibility = View.VISIBLE }
        }
    }

    private fun initListener() {
        with(binding) {
            topBar.setOnBackClickListener { finish() }
            keyboardSyncSignupButton.setOnKeyboardSyncScaleClickListener(98) {}
        }
    }

    companion object {
        const val EXTRA_SOCIAL_PROVIDER = "EXTRA_SOCIAL_PROVIDER"
    }
}