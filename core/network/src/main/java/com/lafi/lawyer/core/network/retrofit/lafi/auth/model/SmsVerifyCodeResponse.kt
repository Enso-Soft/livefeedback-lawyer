package com.lafi.lawyer.core.network.retrofit.lafi.auth.model

import com.lafi.lawyer.core.data.model.auth.SmsVerifyCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsVerifyCodeResponse(
    @SerialName("code") val code: String?
) {
    fun toData(): SmsVerifyCode {
        return SmsVerifyCode(
            code = code ?: ""
        )
    }
}