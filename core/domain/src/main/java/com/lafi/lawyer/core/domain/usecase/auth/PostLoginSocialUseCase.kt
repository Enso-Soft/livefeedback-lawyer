package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.model.common.data.ApiResult
import com.lafi.lawyer.core.model.common.data.ErrorData
import com.lafi.lawyer.core.model.common.login.SocialLoginResult
import com.lafi.lawyer.core.model.common.login.SocialProvider
import javax.inject.Inject

class PostLoginSocialUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        provider: String,
        accessToken: String
    ): SocialLoginResult {
        return when (
            val response = authRepository.loginSocial(
                provider = provider,
                accessToken = accessToken
            )
        ) {
            is ApiResult.Success -> {
                if (response.data) {
                    SocialLoginResult.RequestAccessToke
                } else {
                    SocialProvider.invoke(provider)?.let {
                        SocialLoginResult.NeedSignup(provider = it)
                    } ?: run {
                        SocialLoginResult.Error(
                            error = ErrorData(-1, "NoProvider", "")
                        )
                    }
                }
            }

            is ApiResult.Error -> {
                SocialLoginResult.Error(error = response.error)
            }
        }
    }
}