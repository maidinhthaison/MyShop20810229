package com.ql2.myshop.di

import com.ql2.myshop.data.api.ProductAPI
import com.ql2.myshop.data.repository.product.ProductRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.GetProductUseCaseImpl
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {
    @Singleton
    @Provides
    fun provideProductRepository(
        productAPI: ProductAPI
    ): ProductRepository {
        return ProductRepositoryImpl(
            productAPI = productAPI
        )
    }

    @Singleton
    @Provides
    fun provideProductAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): ProductAPI {
        return retrofitManager[ProductAPI::class.java]
    }

    @Provides
    fun provideGetProductUseCase(productRepository: ProductRepository): GetProductUseCase {
        return GetProductUseCaseImpl(productRepository)
    }
}