package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.domain.model.auth.SocialLoginResult
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import javax.inject.Inject

class PostLoginSocialUseCase @Inject constructor(private val authRepository: AuthRepository) {
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
                SocialLoginResult.RequestAccessToken(userID = response.data)
            }

            is DataResult.Error -> {
                when (response.error.code) {
                    40401 -> SocialLoginResult.NeedSignup(provider)
                    else -> SocialLoginResult.Error(error = response.error)
                }
            }
        }
    }
}