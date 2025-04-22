package com.lafi.lawyer.core.model.common.sms_verify

import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class SmsVerifyRequestResult {
    data class Success(val code: String): SmsVerifyRequestResult()
    data class Error(val error: ErrorData) : SmsVerifyRequestResult()
}

enum class SmsVerifyType(val text: String) {
    SIGNUP("signup")
}