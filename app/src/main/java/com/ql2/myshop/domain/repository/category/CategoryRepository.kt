package com.ql2.myshop.domain.repository.category

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.category.CategoryModel
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<TaskResult<List<CategoryModel>>>
}