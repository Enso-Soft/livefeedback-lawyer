package com.lafi.lawyer.core.data.model.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsVerifyRequest(
    @SerialName("request_type") val requestType: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("request_id") val requestId: String,
    @SerialName("code") val code: String
)
