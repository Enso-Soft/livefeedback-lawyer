package com.lafi.lawyer.core.network.retrofit.lafi.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsVerifyCodeResponse(
    @SerialName("code") val code: String
)