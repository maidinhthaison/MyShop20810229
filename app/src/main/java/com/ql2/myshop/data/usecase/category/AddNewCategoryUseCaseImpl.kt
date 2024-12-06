package com.ql2.myshop.data.usecase.category

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.category.AddCategoryModel
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.domain.usecase.category.AddNewCategoryUseCase
import kotlinx.coroutines.flow.Flow

class AddNewCategoryUseCaseImpl(private val categoryRepository: CategoryRepository) : AddNewCategoryUseCase {
    override fun invoke(cateName : String, cateDes : String): Flow<TaskResult<AddCategoryModel>> {
        return  categoryRepository.addNewCategories(cateName, cateDes)
    }
}