package com.lafi.lawyer.core.network.retrofit.lafi.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginSocialRequest(
    val provider: String,
    @SerialName("access_token")
    val accessToken: String
)