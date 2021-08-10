package com.example.thevampire.deardiary.deardiary.di

import com.example.thevampire.deardiary.deardiary.repositories.AuthService
import com.example.thevampire.deardiary.deardiary.repositories.FirebaseAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthService() : AuthService = FirebaseAuthService()
}