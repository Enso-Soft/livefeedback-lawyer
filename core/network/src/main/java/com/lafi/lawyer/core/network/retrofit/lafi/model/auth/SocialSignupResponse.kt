package com.lafi.lawyer.core.network.retrofit.lafi.model.auth

import com.lafi.lawyer.core.data.model.auth.SocialSignup
import kotlinx.serialization.SerialName

data class SocialSignupResponse(
    @SerialName("signup") val signup: Boolean = false
) {
    fun toData(): SocialSignup {
        return SocialSignup(
            signup = signup
        )
    }
}