package com.ql2.myshop.di

import com.ql2.myshop.data.api.CategoryApi
import com.ql2.myshop.data.repository.category.CategoryRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.category.GetAllCategoryUseCaseImpl
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.domain.usecase.category.GetAllCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {
    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryApi: CategoryApi
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryApi = categoryApi
        )
    }

    @Singleton
    @Provides
    fun provideCategoryAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): CategoryApi {
        return retrofitManager[CategoryApi::class.java]
    }

    @Provides
    fun provideGetAllCategoryUseCase(categoryRepository: CategoryRepository): GetAllCategoryUseCase {
        return GetAllCategoryUseCaseImpl(categoryRepository)
    }
}