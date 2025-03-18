package com.lafi.lawyer.feature.signup

import android.os.Bundle
import androidx.activity.viewModels
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<FeatureSignupActivitySignupBinding>(FeatureSignupActivitySignupBinding::inflate) {
    private val vm by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}