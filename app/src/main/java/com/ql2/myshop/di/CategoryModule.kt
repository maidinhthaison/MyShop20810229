package com.ql2.myshop.di

import com.ql2.myshop.data.api.CategoryAPI
import com.ql2.myshop.data.repository.category.CategoryRepositoryImpl
import com.ql2.myshop.data.retrofit.RetrofitManager
import com.ql2.myshop.data.usecase.category.AddNewCategoryUseCaseImpl
import com.ql2.myshop.data.usecase.category.GetAllCategoryUseCaseImpl
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.domain.usecase.category.AddNewCategoryUseCase
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
        categoryApi: CategoryAPI
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryApi = categoryApi
        )
    }

    @Singleton
    @Provides
    fun provideCategoryAPI(@DefaultApiQualifier retrofitManager: RetrofitManager): CategoryAPI {
        return retrofitManager[CategoryAPI::class.java]
    }

    @Provides
    fun provideGetAllCategoryUseCase(categoryRepository: CategoryRepository): GetAllCategoryUseCase {
        return GetAllCategoryUseCaseImpl(categoryRepository)
    }

    @Provides
    fun provideAddNewCategoryUseCase(categoryRepository: CategoryRepository): AddNewCategoryUseCase {
        return AddNewCategoryUseCaseImpl(categoryRepository)
    }
}