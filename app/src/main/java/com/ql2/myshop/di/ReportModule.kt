package com.ql2.myshop.di

import com.ql2.myshop.data.api.ProductAPI
import com.ql2.myshop.data.api.ReportAPI
import com.ql2.myshop.data.repository.product.ProductRepositoryImpl
import com.ql2.myshop.data.repository.report.ReportRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.report.GetRevenueProfitUseCaseImpl
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.repository.report.ReportRepository
import com.ql2.myshop.domain.usecase.report.GetRevenueProfitUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReportModule {

    @Singleton
    @Provides
    fun provideReportRepository(
        reportAPI: ReportAPI
    ): ReportRepository {
        return ReportRepositoryImpl(
            reportAPI = reportAPI
        )
    }

    @Singleton
    @Provides
    fun provideReportAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): ReportAPI {
        return retrofitManager[ReportAPI::class.java]
    }

    @Provides
    fun provideGetRevenueProfitUseCase(repository: ReportRepository): GetRevenueProfitUseCase {
        return GetRevenueProfitUseCaseImpl(reportRepository = repository)
    }
}