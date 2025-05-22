package com.lafi.lawyer.core.data.model.auth

import com.lafi.lawyer.core.domain.model.auth.SocialSignupData

data class SocialSignup(
    val signup: Boolean
) {
    fun toDomain(): SocialSignupData {
        return SocialSignupData(
            signup = signup
        )
    }
}
