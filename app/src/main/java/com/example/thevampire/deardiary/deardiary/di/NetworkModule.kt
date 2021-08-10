package com.example.thevampire.deardiary.deardiary.di

import com.example.thevampire.deardiary.deardiary.auth.AuthService
import com.example.thevampire.deardiary.deardiary.auth.FirebaseAuthService
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