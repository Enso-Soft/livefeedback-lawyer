package com.lafi.lawyer.core.domain.di

import com.lafi.lawyer.core.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(imp: AuthRepository):
}