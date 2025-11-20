package com.example.cookit.di

import com.example.cookit.data.network.ApiService
import com.example.cookit.data.repository.AuthRepositoryImpl
import com.example.cookit.data.repository.HomeRepositoryImpl
import com.example.cookit.domain.repository.AuthRepository
import com.example.cookit.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService): HomeRepository {
        return HomeRepositoryImpl(apiService)
    }
}
