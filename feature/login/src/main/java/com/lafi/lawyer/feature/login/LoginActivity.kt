package com.lafi.lawyer.feature.login

import android.os.Bundle
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.feature.login.databinding.FeatureLoginActivityLoginBinding

class LoginActivity : BaseActivity<FeatureLoginActivityLoginBinding>(FeatureLoginActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            Log.d("whk__", "카카오 설치 됨")
        }

        initListener()
    }

    private fun initListener() {
        binding.cvGoogleLoginButton.setOnClickListener {  }
        binding.cvKakaoLoginButton.setOnClickListener {
            KakaoLoginDialog.newInstance().show(supportFragmentManager, KakaoLoginDialog.TAG)
        }
    }
}