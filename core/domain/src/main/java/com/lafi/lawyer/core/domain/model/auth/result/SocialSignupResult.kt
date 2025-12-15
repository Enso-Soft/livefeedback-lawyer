package com.lafi.lawyer.core.domain.model.auth.result

sealed class SocialSignupResult {
    data object Success : SocialLoginResult()
}