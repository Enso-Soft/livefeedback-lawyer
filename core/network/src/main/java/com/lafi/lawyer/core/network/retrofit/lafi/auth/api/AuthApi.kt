package com.lafi.lawyer.core.network.retrofit.lafi.auth.api

import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login/social")
    suspend fun postAuthLoginSocial(
        @Body requestBody: AuthLoginSocialRequest
    ): AuthLoginSocialResponse
}