package com.ql2.myshop.di

import com.ql2.myshop.data.local.ConfigServerImpl
import com.ql2.myshop.domain.ConfigServer
import com.ql2.myshop.domain.LocalCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigModule {

    @Singleton
    @Provides
    fun provideConfigServer(cache: LocalCache): ConfigServer {
        return ConfigServerImpl(cache)
    }
}