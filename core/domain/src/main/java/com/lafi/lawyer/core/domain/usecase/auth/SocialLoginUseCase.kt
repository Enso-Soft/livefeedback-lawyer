package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.domain.model.auth.result.SocialLoginResult
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import javax.inject.Inject

class SocialLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        provider: SocialProvider,
        accessToken: String
    ): SocialLoginResult {
        return when (
            val response = authRepository.loginSocial(
                provider = provider,
                accessToken = accessToken
            )
        ) {
            is DataResult.Success -> {
                SocialLoginResult.RequestAccessToken(userID = response.data.userID)
            }

            is DataResult.Error -> {
                when (response.error.code) {
                    NEED_SIGNUP -> SocialLoginResult.NeedSignup(provider)
                    else -> SocialLoginResult.Error(error = response.error)
                }
            }
        }
    }

    companion object {
        private const val NEED_SIGNUP = 40401
        private const val UNSUPPORTED_PROVIDER = 40004
        private const val SOCIAL_TOKEN_UNAUTHORIZED = 40105
    }
}