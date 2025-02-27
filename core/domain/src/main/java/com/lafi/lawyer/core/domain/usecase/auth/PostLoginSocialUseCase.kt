package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.model.NetworkResult
import javax.inject.Inject

class PostLoginSocialUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        provider: String,
        accessToken: String
    ): NetworkResult<Boolean> {
        return authRepository.loginSocial(
            provider = provider,
            accessToken = accessToken
        )
    }
}