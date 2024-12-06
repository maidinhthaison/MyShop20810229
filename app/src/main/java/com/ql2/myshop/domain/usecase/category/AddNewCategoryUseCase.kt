package com.ql2.myshop.domain.usecase.category

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.category.AddCategoryModel
import kotlinx.coroutines.flow.Flow

interface AddNewCategoryUseCase {
    operator fun invoke(cateName : String, cateDes : String): Flow<TaskResult<AddCategoryModel>>
}