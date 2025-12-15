package com.lafi.lawyer.core.network.retrofit.lafi.model.auth

import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class SmsVerifyRequestResponse(
    @SerialName("code") val code: String = "",
    @SerialName("request_id") val requestId: String = "",
    @SerialName("expires_at") val expiresAt: String
) {
    fun toData(): SmsVerifyRequestCode {
        return SmsVerifyRequestCode(
            code = code,
            requestId = requestId,
            expiresAt = Instant.parse(expiresAt)
        )
    }
}