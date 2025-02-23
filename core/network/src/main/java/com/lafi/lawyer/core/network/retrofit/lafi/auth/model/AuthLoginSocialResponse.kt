package com.lafi.lawyer.core.network.retrofit.lafi.auth.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginSocialResponse(
    @SerializedName("user_exists")
    val userExists: Boolean
)