package com.lafi.lawyer.core.data.model.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequest(
    @SerialName("provider") val provider: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("request_id") val requestID: String
)