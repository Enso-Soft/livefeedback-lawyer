package com.lafi.lawyer.core.network.retrofit.lafi.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsVerifyCodeRequest(
    @SerialName("request_type") val requestType: String,
    @SerialName("phone_number") val phoneNumber: String
)