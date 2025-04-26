package com.lafi.lawyer.core.network.retrofit.lafi.model.auth

import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsVerifyRequestResponse(
    @SerialName("code") val code: String = ""
) {
    fun toData(): SmsVerifyRequestCode {
        return SmsVerifyRequestCode(
            code = code
        )
    }
}