package com.lafi.lawyer.core.domain.model.auth

import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class SmsVerifyRequestResult {
    data class Success(val code: String): SmsVerifyRequestResult()
    data class Error(val error: ErrorData) : SmsVerifyRequestResult()
}