package com.lafi.lawyer.core.model.common.login

import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class SocialLoginResult {
    data object RequestAccessToke : SocialLoginResult()
    data class NeedSignup(val provider: SocialProvider) : SocialLoginResult()
    data class Error(val error: ErrorData) : SocialLoginResult()
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