package com.lafi.lawyer.core.data.model.auth

import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestData
import java.time.Instant

data class SmsVerifyRequestCode(
    val code: String,
    val requestId: String,
    val expiresAt: Instant
) {
    fun toDomain(): SmsVerifyRequestData {
        return SmsVerifyRequestData(
            code = code,
            requestId = requestId,
            expiresAt = expiresAt
        )
    }
}