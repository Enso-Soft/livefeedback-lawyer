package com.lafi.lawyer.core.network.retrofit.lafi.auth.model

import com.lafi.lawyer.core.data.model.auth.AuthLoginSocial
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginSocialResponse(
    @SerialName("user_id") val userID: String = ""
) {
    fun toData(): AuthLoginSocial {
        return AuthLoginSocial(
            userID = userID
        )
    }
}