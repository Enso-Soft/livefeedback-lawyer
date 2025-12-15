package com.lafi.lawyer.core.network.retrofit.lafi.model.auth

import com.lafi.lawyer.core.data.model.auth.SocialLogin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginResponse(
    @SerialName("user_id") val userID: String = ""
) {
    fun toData(): SocialLogin {
        return SocialLogin(
            userID = userID
        )
    }
}