package com.lafi.lawyer.core.network.retrofit.lafi.model.auth

import android.util.Log
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class SmsVerifyRequestResponse(
    @SerialName("code") val code: String = "",
    @SerialName("expires_at") val expiresAt: String
) {
    fun toData(): SmsVerifyRequestCode {
        Log.d("whk__", "expiresAt : $expiresAt")

        return SmsVerifyRequestCode(
            code = code,
            expiresAt = Instant.parse(expiresAt + "Z")
        )
    }
}