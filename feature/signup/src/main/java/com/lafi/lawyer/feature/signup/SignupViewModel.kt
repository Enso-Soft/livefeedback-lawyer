package com.lafi.lawyer.feature.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.usecase.auth.SmsVerifyRequestUseCase
import com.lafi.lawyer.core.domain.usecase.auth.SmsVerifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val smsVerifyRequestUseCase: SmsVerifyRequestUseCase,
    private val smsVerifyUseCase: SmsVerifyUseCase
) : ViewModel() {
    private var _smsVerifyCode: String? = null
    val smsVerifyCode: String? get() = _smsVerifyCode

    /** 임시 테스트 코드 **/
    private val _tempCodeEvent = MutableSharedFlow<String>()
    val tempCodeEvent: SharedFlow<String> get() = _tempCodeEvent

    fun sendIntent(intent: SignupIntent) {
        when (intent) {
            is SignupIntent.RequestSmsVerifyCode -> requestSmsVerifyCode(intent.phoneNumber)
        }
    }

    private var smsVerifyRequestJob: Job? = null
    private fun requestSmsVerifyCode(phoneNumber: String) = viewModelScope.launch(Dispatchers.Main.immediate) {
        /** 중복 호출 방지를 위한 체크 */
        if (smsVerifyRequestJob?.isActive == true) return@launch

        smsVerifyRequestJob = launch(Dispatchers.IO) {
            when (val result = smsVerifyRequestUseCase(
                smsVerifyType = SmsVerifyType.SIGNUP,
                phoneNumber = phoneNumber.filter { it.isDigit() }
            )) {
                is SmsVerifyRequestResult.Success -> {
                    _tempCodeEvent.emit(result.code)

                    val response = smsVerifyUseCase(
                        smsVerifyType = SmsVerifyType.SIGNUP,
                        phoneNumber = phoneNumber.filter { it.isDigit() },
                        code = result.code
                    )
                    Log.d("whk__", "response : $response")
                }
                is SmsVerifyRequestResult.Error -> {}
            }
        }
    }
}