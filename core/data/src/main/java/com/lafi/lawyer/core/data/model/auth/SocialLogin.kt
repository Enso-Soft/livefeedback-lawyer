package com.lafi.lawyer.core.data.model.auth

import com.lafi.lawyer.core.domain.model.auth.SocialLoginData

data class SocialLogin(
    val userID: String
) {
    fun toDomain(): SocialLoginData {
        return SocialLoginData(
            userID = userID
        )
    }
}
