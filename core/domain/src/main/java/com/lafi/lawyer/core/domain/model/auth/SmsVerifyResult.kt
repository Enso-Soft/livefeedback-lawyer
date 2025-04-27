package com.lafi.lawyer.core.domain.model.auth

import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class SmsVerifyResult {
    data object Success : SmsVerifyResult()
    data object InvalidVerifyCode : SmsVerifyResult()
    data object InvalidFormatPhoneNumber : SmsVerifyResult()
    data class Error(val error: ErrorData) : SmsVerifyResult()
}