package com.lafi.lawyer.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.core.design_system.component.scale_ripple.setScaleRipple
import com.lafi.lawyer.core.model.common.login.SocialLoginResult
import com.lafi.lawyer.feature.login.databinding.FeatureLoginActivityLoginBinding
import com.lafi.lawyer.feature.login.kakao_login.KakaoLoginDialog
import com.lafi.lawyer.feature.login.kakao_login.OnKakaoLoginDialogListener
import com.lafi.lawyer.feature.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity : BaseActivity<FeatureLoginActivityLoginBinding>(FeatureLoginActivityLoginBinding::inflate) {
    private val vm by viewModels<LoginViewModel>()

    private val isAvailableKakaoApplicationLogin: Boolean
        get() = UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)

    private val singleKakaoLoginFragment by lazy { createKakaoLoginFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Log.d("lafi", "카카오 키 해시 : ${Utility.getKeyHash(this@LoginActivity)}")
        }

        setupUi()
        subscribe()
        initListener()
    }

    private fun setupUi() {
        binding.llKakaoLoginButton.setScaleRipple(true)
    }

    private fun subscribe() {
        with(vm) {
            socialLoginResultEvent
                .onEach {
                    setOnSocialLoginResult(it)
                }
                .launchIn(lifecycleScope)
        }
    }

    private fun initListener() {
        with(binding) {
            llGoogleLoginButton.setOnClickListener {  }
            llKakaoLoginButton.setOnClickListener {
                if (isAvailableKakaoApplicationLogin) {
                    singleKakaoLoginFragment.show(supportFragmentManager, KakaoLoginDialog.TAG)
                } else {
                    processAnotherKakaoLogin()
                }
            }
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

    private fun setOnSocialLoginResult(event: SocialLoginResult) {
        when (event) {
            is SocialLoginResult.NeedSignup -> {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }

            is SocialLoginResult.Error -> {
                Toast.makeText(this@LoginActivity, event.error.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    /** 카카오 앱을 통한 로그인 */
    private fun processKakaoLogin() {
        if (isAvailableKakaoApplicationLogin) {
            UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity) { token, error ->
                resultKakaoLogin(token, error)
            }
        } else {
            processAnotherKakaoLogin()
        }
    }

    /** 카카오 로그인 (인터넷) 페이지를 통한 로그인 */
    private fun processAnotherKakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = { token, error ->
            resultKakaoLogin(token, error)
        })
    }

    private fun resultKakaoLogin(token: OAuthToken?, error: Throwable?) {
        if (token != null && error == null) {
            UserApiClient.instance.me { user, meError ->
                if (user != null && meError == null) {
                    vm.checkSocialLogin(
                        provider = "kakao",
                        socialAccessToken = token.accessToken
                    )
                } else {
                    Toast.makeText(this@LoginActivity, "카카오 로그인 성공 했으나 정보 얻기 실패", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Log.d("whk__", "카카오 왜? : ${error?.message}")
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