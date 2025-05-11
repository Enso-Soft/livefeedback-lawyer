package com.lafi.lawyer.core.domain.model.auth

import com.lafi.lawyer.core.model.common.data.ErrorData
import java.time.Instant

sealed class SmsVerifyRequestResult {
    data class Success(val code: String, val expiresAt: Instant): SmsVerifyRequestResult()
    data object DuplicationRequest: SmsVerifyRequestResult()
    data class Error(val error: ErrorData) : SmsVerifyRequestResult()
}