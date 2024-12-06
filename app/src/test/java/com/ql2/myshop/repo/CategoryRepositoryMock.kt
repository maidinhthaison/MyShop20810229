package com.ql2.myshop.repo

import com.ql2.myshop.data.GetCategoryResponseMock
import com.ql2.myshop.domain.AppError
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.category.AddCategoryModel
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.repository.category.CategoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryMock : CategoryRepository{
    private var isError = false
    private var listCategoryModel: List<CategoryModel>
    private var categoryModelMock : GetCategoryResponseMock.MockGetAllCategoryResponseDTO = GetCategoryResponseMock.MockGetAllCategoryResponseDTO()
    init {
        listCategoryModel = listOf(CategoryModel(
            cateId = categoryModelMock.cateId,
            cateName = categoryModelMock.cateName,
            cateDescription = categoryModelMock.cateDescription))


    }

    override fun getAllCategories(): Flow<TaskResult<List<CategoryModel>>> {
        return flow {
            emit(TaskResult.Loading)
            delay(2000L)
            if (isError) {
                emit(TaskResult.Failure(AppError.GeneralError(NullPointerException())))
            } else {
                val result = categoryModelMock.results
                emit(TaskResult.Success(result))
            }
        }
    }

    override fun addNewCategories(
        cateName: String,
        cateDescription: String
    ): Flow<TaskResult<AddCategoryModel>> {
        TODO("Not yet implemented")
    }

}
