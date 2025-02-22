package com.lafi.lawyer.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Named("LAFI_API_URL")
    @Provides
    fun provideApiUrl(): String = BuildConfig.LAFI_API_URL
}