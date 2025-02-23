package com.lafi.lawyer.core.network.retrofit.lafi.auth

import com.lafi.lawyer.core.network.retrofit.lafi.NetworkResult
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialResponse

interface AuthDataSource {
    suspend fun postLoginSocial(requestBody: AuthLoginSocialRequest): NetworkResult<AuthLoginSocialResponse>
}