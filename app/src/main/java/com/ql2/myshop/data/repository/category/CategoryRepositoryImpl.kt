package com.ql2.myshop.data.repository.category

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.CategoryAPI
import com.ql2.myshop.data.api.request.AddCategoryRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.category.AddCategoryModel
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.repository.category.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepositoryImpl (private val categoryApi: CategoryAPI,
                              private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : CategoryRepository {

    override fun getAllCategories(): Flow<TaskResult<List<CategoryModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            categoryApi.getAllCategories()
        }.map { it -> it.map { it.toCategoryModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun addNewCategories(cateName: String, cateDes: String): Flow<TaskResult<AddCategoryModel>> = flow<TaskResult<AddCategoryModel>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            val addCategoryRequestDTO =AddCategoryRequestDTO(cateName =cateName, cateDescription = cateDes)
            categoryApi.insertNewCategory(addCategoryRequestDTO)
        }.map { it.toAddCategoryModel() }
        emit(result)
    }.flowOn(defaultDispatcher)


}