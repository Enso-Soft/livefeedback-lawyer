package com.lafi.lawyer.core.network.di

import com.lafi.lawyer.core.network.retrofit.RetrofitLafiAuth
import com.lafi.lawyer.core.network.retrofit.lafi.auth.AuthDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideLafiAuth(
        okhttpClient: OkHttpClient,
        networkJson: Json
    ): AuthDataSource = RetrofitLafiAuth(
        okhttpClient,
        networkJson
    )
}