package com.ql2.myshop.di

import com.ql2.myshop.data.api.CategoryApi
import com.ql2.myshop.data.api.OrdersAPI
import com.ql2.myshop.data.repository.category.CategoryRepositoryImpl
import com.ql2.myshop.data.repository.orders.OrdersRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.category.GetAllCategoryUseCaseImpl
import com.ql2.myshop.data.usecase.orders.SearchOrderUseCaseImpl
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.domain.repository.orders.OrdersRepository
import com.ql2.myshop.domain.usecase.category.GetAllCategoryUseCase
import com.ql2.myshop.domain.usecase.orders.SearchOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OrdersModule {
    @Singleton
    @Provides
    fun provideOrdersRepository(
        ordersAPI: OrdersAPI
    ): OrdersRepository {
        return OrdersRepositoryImpl(
            ordersAPI = ordersAPI
        )
    }

    @Singleton
    @Provides
    fun provideOrdersAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): OrdersAPI {
        return retrofitManager[OrdersAPI::class.java]
    }

    @Provides
    fun provideSearchOrderUseCase(ordersRepository: OrdersRepository): SearchOrderUseCase {
        return SearchOrderUseCaseImpl(ordersRepository)
    }
}