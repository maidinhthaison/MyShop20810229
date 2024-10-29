package com.ql2.myshop.di

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ql2.myshop.config.AppConfig
import com.ql2.myshop.data.LocalCacheImpl
import com.ql2.myshop.data.network.ConnectivityDataSource
import com.ql2.myshop.data.retrofit.RetrofitManager
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
        gson: Gson, connectivityDataSource: ConnectivityDataSource
    ): RetrofitManager {
        return RetrofitManager(
            gson = gson,
            connectivityDataSource = connectivityDataSource,
            baseUrl = AppConfig.backendEnvironment.baseUrl
        )
    }

}