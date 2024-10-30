package com.ql2.myshop.data.usecase.category

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.domain.usecase.category.GetAllCategoryUseCase
import kotlinx.coroutines.flow.Flow

class GetAllCategoryUseCaseImpl(private val categoryRepository: CategoryRepository) :
    GetAllCategoryUseCase {
    override fun invoke(): Flow<TaskResult<List<CategoryModel>>> {
        return categoryRepository.getAllCategories()
    }
}