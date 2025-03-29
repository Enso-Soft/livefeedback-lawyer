package com.lafi.lawyer.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafi.lawyer.core.domain.usecase.signup.PostSmsVerifyRequestUseCase
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val postSmsVerifyRequestUseCase: PostSmsVerifyRequestUseCase
) : ViewModel() {
    private var smsVerifyRequestJob: Job? = null
    fun smsVerifyRequest() = viewModelScope.launch(Dispatchers.IO) {
        /** 중복 호출 방지를 위한 체크 */
        if (smsVerifyRequestJob?.isActive == true) return@launch

        smsVerifyRequestJob = launch {
            postSmsVerifyRequestUseCase.invoke(
                smsVerifyType = SmsVerifyType.SIGNUP,
                phoneNumber = "01085869739"
            )
        }
    }
}