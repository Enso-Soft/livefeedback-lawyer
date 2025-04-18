package com.lafi.lawyer.core.network.di

import com.lafi.lawyer.core.network.retrofit.RetrofitLafiAuth
import com.lafi.lawyer.core.network.retrofit.lafi.auth.AuthDataSource
import com.lafi.lawyer.core.network.retrofit.lafi.auth.api.AuthApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun provideLafiAuth(authDataSource: RetrofitLafiAuth): AuthDataSource
}