package com.lafi.lawyer.core.domain.model.auth.result

import com.lafi.lawyer.core.model.common.data.ErrorData
import java.time.Instant

sealed class SmsVerifyRequestResult {
    data class Success(val code: String, val requestId: String, val expiresAt: Instant): SmsVerifyRequestResult()
    data class DuplicationRequest(val expiresAt: Instant?): SmsVerifyRequestResult()
    data class Error(val error: ErrorData) : SmsVerifyRequestResult()
}