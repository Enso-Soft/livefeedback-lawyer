package com.lafi.lawyer.core.network.di

import com.lafi.lawyer.core.network.BuildConfig
import com.lafi.lawyer.core.network.retrofit.RetrofitLafiAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val CONNECT_TIME_OUT_SEC = 30
    private const val READ_TIME_OUT_SEC = 30

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Named("LAFI_API_URL")
    @Provides
    fun provideApiUrl(): String = BuildConfig.LAFI_API_URL

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout((READ_TIME_OUT_SEC).toLong(), TimeUnit.SECONDS)
            .connectTimeout((CONNECT_TIME_OUT_SEC).toLong(), TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            ).addInterceptor(
                Interceptor { chain ->
                    with(chain) {
                        try {
                            // 여기에서 헤더 값 추가.
                            val newRequest = request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                //                                .addHeader(
                                //                                    "Authorization",
                                //                                    "Bearer ${token}"
                                //                                )
                                .build()
                            proceed(newRequest)
                        } catch (e: Exception) {
                            proceed(request())
                        }
                    }
                }
            ).build()
    }
}