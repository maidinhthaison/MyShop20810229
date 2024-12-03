package com.ql2.myshop.di

import com.ql2.myshop.data.api.ProductAPI
import com.ql2.myshop.data.repository.product.ProductRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.product.AddProductUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductByCateAndNameUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductByCateUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductByNameUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductUseCaseImpl
import com.ql2.myshop.data.usecase.product.UpdateProductByIdUseCaseImpl
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.AddProductUseCase
import com.ql2.myshop.domain.usecase.product.GetProductByCateAndNameUseCase
import com.ql2.myshop.domain.usecase.product.GetProductByCateUseCase
import com.ql2.myshop.domain.usecase.product.GetProductByNameUseCase
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import com.ql2.myshop.domain.usecase.product.UpdateProductByIdUseCase
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

    @Provides
    fun provideGetProductByNameUseCase(productRepository: ProductRepository): GetProductByNameUseCase {
        return GetProductByNameUseCaseImpl(productRepository)
    }

    @Provides
    fun provideGetProductByCateUseCase(productRepository: ProductRepository): GetProductByCateUseCase {
        return GetProductByCateUseCaseImpl(productRepository)
    }

    @Provides
    fun provideGetProductByCateAndNameUseCase(productRepository: ProductRepository): GetProductByCateAndNameUseCase {
        return GetProductByCateAndNameUseCaseImpl(productRepository)
    }

    @Provides
    fun provideUpdateProductByIdUseCase(productRepository: ProductRepository): UpdateProductByIdUseCase {
        return UpdateProductByIdUseCaseImpl(productRepository)
    }

    @Provides
    fun provideAddProductUseCase(productRepository: ProductRepository): AddProductUseCase {
        return AddProductUseCaseImpl(productRepository)
    }
}