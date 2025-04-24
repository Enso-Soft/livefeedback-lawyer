package com.lafi.lawyer.feature.signup

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.sdk.user.UserApiClient
import com.lafi.lawyer.core.design_system.R
import com.lafi.lawyer.core.design_system.activity.BaseActivity
import com.lafi.lawyer.core.design_system.component.scale_ripple.setOnKeyboardSyncScaleClickListener
import com.lafi.lawyer.core.design_system.component.slid_in.slideInFromRight
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.feature.signup.databinding.FeatureSignupActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class SignupActivity : BaseActivity<FeatureSignupActivitySignupBinding>(FeatureSignupActivitySignupBinding::inflate) {
    private val vm by viewModels<SignupViewModel>()

    private val textInputViews: List<View> by lazy {
        listOf(
            binding.tiName,
            binding.tiEmail,
            binding.tiPhoneNumber,
        )
    }

    private val enterSocialProvider by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_SOCIAL_PROVIDER, EnterType::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_SOCIAL_PROVIDER)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        subscribeUi()
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

        with(binding.tiPhoneNumber.editText) {
            inputType = InputType.TYPE_CLASS_NUMBER
            addTextChangedListener(PhoneNumberFormattingTextWatcher())
        }
    }

    private fun subscribeUi() {
        vm.tempCodeEvent
            .onEach { Toast.makeText(this@SignupActivity, "코드 : $it", Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)
    }

    private fun initListener() {
        with(binding) {
            topBar.setOnBackClickListener { setOnExit() }
            keyboardSyncSignupButton.setOnKeyboardSyncScaleClickListener(98) { setOnSignupButton() }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setOnExit()
            }
        })

    }

    private fun setOnExit() {
        when (enterSocialProvider?.type) {
            SocialProvider.KAKAO -> UserApiClient.instance.unlink {}
            else -> {}
        }

        finish()
    }

    private fun setOnSignupButton() {
        val phoneNumber = binding.tiPhoneNumber.editText.text.toString()
        if (phoneNumber.isBlank()) {
            Toast.makeText(this@SignupActivity, "핸드폰 번호를 입력 해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        vm.sendIntent(SignupIntent.RequestSmsVerifyCode(phoneNumber = phoneNumber))
    }

    @Parcelize
    data class EnterType(val type: SocialProvider) : Parcelable

    companion object {
        const val EXTRA_SOCIAL_PROVIDER = "EXTRA_SOCIAL_PROVIDER"
    }
}