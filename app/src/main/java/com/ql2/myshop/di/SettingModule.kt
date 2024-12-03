package com.ql2.myshop.di

import com.ql2.myshop.data.local.SettingAppImpl
import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.local.SettingApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingModule {
    @Singleton
    @Provides
    fun provideSettingModule(cache: LocalCache): SettingApp {
        return SettingAppImpl(cache)
    }
}