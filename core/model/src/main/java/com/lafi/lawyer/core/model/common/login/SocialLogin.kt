package com.lafi.lawyer.core.model.common.login

import com.lafi.lawyer.core.model.common.ApiError

sealed class SocialLoginResult {
    data class Error(val error: ApiError) : SocialLoginResult()
    data class NeedSignup(val provider: SocialProvider) : SocialLoginResult()
    data object RequestAccessToke : SocialLoginResult()
}

enum class SocialProvider(val providerName: String) {
    KAKAO(providerName = "kakao"),
    GOOGLE(providerName = "google")
    ;

    companion object {
        operator fun invoke(name: String) : SocialProvider? {
            return entries.find { it.providerName == name }
        }
    }
}