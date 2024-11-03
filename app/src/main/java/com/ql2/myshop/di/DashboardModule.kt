package com.ql2.myshop.di

import com.ql2.myshop.data.api.DashboardAPI
import com.ql2.myshop.data.repository.dashboard.DashboardRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.dashboard.GetNumberOfProductByCateIdUseCaseImpl
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetNumberOfProductByCateIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DashboardModule {
    @Singleton
    @Provides
    fun provideDashboardRepository(
        dashboardAPI: DashboardAPI
    ): DashboardRepository {
        return DashboardRepositoryImpl(
            dashboardAPI = dashboardAPI
        )
    }

    @Singleton
    @Provides
    fun provideDashboardAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): DashboardAPI {
        return retrofitManager[DashboardAPI::class.java]
    }

    @Provides
    fun provideGetNumberOfProductByCateId(dashboardRepository: DashboardRepository): GetNumberOfProductByCateIdUseCase {
        return GetNumberOfProductByCateIdUseCaseImpl(dashboardRepository)
    }
}