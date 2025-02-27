package com.lafi.laywer.core.data.di

import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.laywer.core.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(imp: AuthRepositoryImpl): AuthRepository
}