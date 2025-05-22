package com.lafi.lawyer.core.domain.model.auth.result

import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class SocialLoginResult {
    data class RequestAccessToken(val userID: String) : SocialLoginResult()
    data class NeedSignup(val provider: SocialProvider) : SocialLoginResult()
    data class Error(val error: ErrorData) : SocialLoginResult()
}