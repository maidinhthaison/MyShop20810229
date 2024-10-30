package com.ql2.myshop.domain.usecase.category

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface GetAllCategoryUseCase {
    operator fun invoke(): Flow<TaskResult<List<CategoryModel>>>
}