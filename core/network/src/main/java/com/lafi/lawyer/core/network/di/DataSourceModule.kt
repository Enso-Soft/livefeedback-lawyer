package com.lafi.lawyer.core.network.di

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.network.datasource.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsAuthRemoteDataSource(impl: AuthDataSourceImpl): AuthRemoteDataSource
}