package com.lafi.lawyer.core.network.di

import com.lafi.lawyer.core.network.retrofit.lafi.auth.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LafiApiModule {
    @Provides
    @Singleton
    fun provideLafiAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}