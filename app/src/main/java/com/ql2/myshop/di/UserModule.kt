package com.ql2.myshop.di

import android.content.Context
import android.content.SharedPreferences
import com.ql2.myshop.data.LocalCacheImpl
import com.ql2.myshop.data.UserAppSessionImpl
import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.UserAppSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return LocalCacheImpl.getSharedPreferences(context, "APP_SESSION_PREFS_SECURE")
    }

    @Singleton
    @Provides
    fun provideLocalCache(sharedPreferences: SharedPreferences): LocalCache {
        return LocalCacheImpl(sharedPreferences)
    }
}