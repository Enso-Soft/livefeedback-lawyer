package com.lafi.lawyer.core.data.model.auth.request

import kotlinx.serialization.SerialName

data class SocialSignupRequest(
    @SerialName("provider") val provider: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("request_id") val requestID: String,
    @SerialName("email") val email: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("name") val name: String
)