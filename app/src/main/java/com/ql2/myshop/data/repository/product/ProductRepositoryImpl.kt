package com.ql2.myshop.data.repository.product

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.ProductAPI
import com.ql2.myshop.data.api.request.AddProductRequestDTO
import com.ql2.myshop.data.api.request.GetAllProductRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.data.api.request.UpdateProductRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.product.AddProductModel
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class ProductRepositoryImpl (private val productAPI: ProductAPI,
                             private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : ProductRepository {

    override fun getAllProducts(getAllProductRequestDTO: GetAllProductRequestDTO): Flow<TaskResult<List<ProductModel>>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            productAPI.getAllProducts(limit = getAllProductRequestDTO.limit, offset = getAllProductRequestDTO.offset)
        }.map { it -> it.map { it.toProductModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getProductsByCate(
        getProductByCateRequestDTO: GetProductByCateRequestDTO
    ): Flow<TaskResult<List<ProductModel>>> = flow{
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            productAPI.getProductByCate(cateId = getProductByCateRequestDTO.cateId,
                limit = getProductByCateRequestDTO.limit, offset = getProductByCateRequestDTO.offset)
        }.map { it -> it.map { it.toProductModel() } }
        emit(result)
    }

    override fun getProductsByName(getProductByNameRequestDTO: GetProductByNameRequestDTO):
            Flow<TaskResult<List<ProductModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            productAPI.getProductByName(proName = getProductByNameRequestDTO.proName, limit =
            getProductByNameRequestDTO.limit, offset = getProductByNameRequestDTO.offset)
        }.map { it -> it.map { it.toProductModel() } }
        emit(result)
    }

    override fun getProductsByCateAndName(getProductByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO):
            Flow<TaskResult<List<ProductModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            productAPI.getProductByCateAndName(cateId = getProductByCateAndNameRequestDTO.cateId,
                proName = getProductByCateAndNameRequestDTO.proName,
                limit = getProductByCateAndNameRequestDTO.limit,
                offset = getProductByCateAndNameRequestDTO.offset)
        }.map { it -> it.map { it.toProductModel() } }
        emit(result)
    }


    override fun updateProductById(
        productId: Int,
        salePrice: Int,
        quantity: Int,
        description: String,
        productName: String
    ): Flow<TaskResult<UpdateProductByIdModel>> = flow {
        emit(TaskResult.Loading)
        val updateProductRequestDTO = UpdateProductRequestDTO(salePrice, quantity, description, productName)
        val result = SafeCallAPI.callApi {
            productAPI.updateProductById(productId, updateProductRequestDTO)
        }.map { it.toUpdateProductByIdModel()}
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun addProduct(
        cateId: Int,
        importPrice: Int,
        quantity: Int,
        description: String,
        productName: String,
        productImage: String,
        salePrice: Int
    ): Flow<TaskResult<AddProductModel>> = flow<TaskResult<AddProductModel>> {
        emit(TaskResult.Loading)
        val addProductRequestDTO = AddProductRequestDTO(cateId = cateId, importPrice = importPrice,
            salePrice = salePrice, quantity = quantity, description = description,
            productName = productName, productImage = productImage)
        val result = SafeCallAPI.callApi {
            productAPI.addNewProduct(request = addProductRequestDTO)
        }.map { it.toAddProductModel()}
        emit(result)
    }.flowOn(defaultDispatcher)


}