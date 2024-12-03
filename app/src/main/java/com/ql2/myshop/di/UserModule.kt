package com.ql2.myshop.di

import com.ql2.myshop.data.local.UserAppSessionImpl
import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.local.UserAppSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Singleton
    @Provides
    fun provideUserAppSession(cache: LocalCache): UserAppSession {
        return UserAppSessionImpl(cache)
    }


}