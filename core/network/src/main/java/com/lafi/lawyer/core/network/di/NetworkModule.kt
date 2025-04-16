package com.lafi.lawyer.core.network.di

import com.lafi.lawyer.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
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
    fun provideLafiHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originRequest = chain.request()

            val requestBuilder = originRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-OS", "android")

            try {
                chain.proceed(requestBuilder.method(originRequest.method, originRequest.body).build())
            } catch (_: Exception) {
                chain.proceed(originRequest)
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(lafiHeaderInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout((READ_TIME_OUT_SEC).toLong(), TimeUnit.SECONDS)
            .connectTimeout((CONNECT_TIME_OUT_SEC).toLong(), TimeUnit.SECONDS)
            .addInterceptor(lafiHeaderInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            ).build()
    }

    @Provides
    @Singleton
    fun provideLafiRetrofit(
        okhttpClient: OkHttpClient,
        networkJson: Json
    ): Retrofit {
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(BuildConfig.LAFI_API_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}