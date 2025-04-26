package com.lafi.lawyer.core.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsVerifyRequestCodeRequest(
    @SerialName("request_type") val requestType: String,
    @SerialName("phone_number") val phoneNumber: String
)