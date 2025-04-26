package com.lafi.lawyer.core.domain.model.auth

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