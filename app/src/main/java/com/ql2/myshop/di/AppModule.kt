package com.ql2.myshop.di

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ql2.myshop.config.AppConfig
import com.ql2.myshop.data.local.LocalCacheImpl
import com.ql2.myshop.data.network.ConnectivityDataSource
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.local.ConfigServer
import com.ql2.myshop.utils.FileUtils
import com.ql2.myshop.utils.FileUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideConnectivityDataSource(
        @ApplicationContext context: Context
    ): ConnectivityDataSource {
        return ConnectivityDataSource(
            applicationContext = context
        )
    }

    @DefaultApiQualifier
    @Provides
    fun provideRetrofitManager(
        gson: Gson, connectivityDataSource: ConnectivityDataSource, configServer: ConfigServer
    ): RetrofitManager {
        return RetrofitManager(
            gson = gson,
            connectivityDataSource = connectivityDataSource,
            configServer = configServer
        )
    }

    // File Utils
    @Provides
    @Singleton
    fun provideFileUtils(
        @ApplicationContext context: Context
    ): FileUtils {
        return FileUtilsImpl(
            context = context
        )
    }

    // Local Cache
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