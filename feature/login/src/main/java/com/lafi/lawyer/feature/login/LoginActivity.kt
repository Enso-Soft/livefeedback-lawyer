package com.lafi.lawyer.feature.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.feature.login.databinding.FeatureLoginActivityLoginBinding
import com.lafi.lawyer.feature.login.kakao_login.KakaoLoginDialog
import com.lafi.lawyer.feature.login.kakao_login.OnKakaoLoginDialogListener

class LoginActivity : BaseActivity<FeatureLoginActivityLoginBinding>(FeatureLoginActivityLoginBinding::inflate) {
    private val singleKakaoLoginFragment by lazy { createKakaoLoginFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            Log.d("whk__", "카카오 설치 됨")
        }

        initListener()
    }

    override fun onResume() {
        super.onResume()

        UserApiClient.instance.logout {  }
    }

    private fun initListener() {
        binding.cvGoogleLoginButton.setOnClickListener {  }
        binding.cvKakaoLoginButton.setOnClickListener {
            singleKakaoLoginFragment.show(supportFragmentManager, KakaoLoginDialog.TAG)
        }

        singleKakaoLoginFragment.setOnKakaoLoginDialogListener(object : OnKakaoLoginDialogListener {
            override fun onKakaoLogin() {
                processKakaoLogin()
            }

            override fun onAnotherLogin() {
                processAnotherKakaoLogin()
            }
        })
    }

    private fun processKakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                resultKakaoLogin(token, error)
            }
        } else {
            processAnotherKakaoLogin()
        }
    }

    private fun processAnotherKakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = { token, error ->
            resultKakaoLogin(token, error)
        })
    }

    private fun resultKakaoLogin(token: OAuthToken?, error: Throwable?) {
        if (token != null && error == null) {
            UserApiClient.instance.me { user, meError ->
                if (user != null && meError == null) {
                    token.accessToken
                    Toast.makeText(this@LoginActivity, "${token.accessToken}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "카카오 로그인 성공 했으나 정보 얻기 실패", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this@LoginActivity, "카카오 로그인 실패", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createKakaoLoginFragment(): KakaoLoginDialog {
        return supportFragmentManager
            .findFragmentByTag(KakaoLoginDialog.TAG)
            ?.let { it as KakaoLoginDialog }
            ?: KakaoLoginDialog.newInstance()
    }
}