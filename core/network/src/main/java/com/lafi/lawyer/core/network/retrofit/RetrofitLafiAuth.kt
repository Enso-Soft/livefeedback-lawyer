package com.lafi.lawyer.core.network.retrofit

import com.lafi.lawyer.core.model.common.NetworkResult
import com.lafi.lawyer.core.network.BuildConfig
import com.lafi.lawyer.core.network.retrofit.lafi.auth.AuthDataSource
import com.lafi.lawyer.core.network.retrofit.lafi.auth.api.AuthApi
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialResponse
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeResponse
import com.lafi.lawyer.core.network.retrofit.lafi.safeApiCall
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitLafiAuth @Inject constructor(
    okhttpFactory: OkHttpClient,
    private val networkJson: Json
): AuthDataSource {
    private val networkApi = Retrofit.Builder()
        .client(okhttpFactory)
        .baseUrl(BuildConfig.LAFI_API_URL)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(AuthApi::class.java)

    override suspend fun postLoginSocial(
        requestBody: AuthLoginSocialRequest
    ): NetworkResult<AuthLoginSocialResponse> {
        return safeApiCall(networkJson) {
            networkApi.postAuthLoginSocial(requestBody)
        }
    }

    override suspend fun postSmsVerifyCode(
        requestBody: SmsVerifyCodeRequest
    ): NetworkResult<SmsVerifyCodeResponse> {
        return safeApiCall(networkJson) {
            networkApi.postSmsVerifyCode(requestBody)
        }
    }
}