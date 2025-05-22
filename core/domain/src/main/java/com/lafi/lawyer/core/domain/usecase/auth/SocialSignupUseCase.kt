package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.domain.model.auth.result.SocialSignupResult
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class SocialSignupUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        provider: SocialProvider,
        accessToken: String,
        requestId: String,
        email: String,
        phoneNumber: String,
        name: String,
    ): SocialSignupResult {
        authRepository.signupSocial(
            provider = provider,
            accessToken = accessToken,
            requestId = requestId,
            email = email,
            phoneNumber = phoneNumber,
            name = name
        ).let { reponse ->

        }
    }

    companion object {
        private const val UNSUPPORTED_PROVIDER = 40004 // 지원하지 않는 Provider입니다.
        private const val SOCIAL_TOKEN_UNAUTHORIZED = 40106 // 소셜 토큰이 옳바르지 않습니다.
        private const val USER_NOT_FOUND = 40401 // 사용자를 찾을 수 없습니다.
    }
}