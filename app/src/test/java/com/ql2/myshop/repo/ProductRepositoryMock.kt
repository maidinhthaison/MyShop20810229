package com.ql2.myshop.repo

import com.ql2.myshop.data.ProductResponseMock
import com.ql2.myshop.data.api.request.GetAllProductRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.domain.AppError
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.AddProductModel
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryMock : ProductRepository {
    private var isError = false
    private var listProductModel: List<ProductModel>

    private var productResponseMock : ProductResponseMock.MockGetAllProductResponseDTO =
        ProductResponseMock.MockGetAllProductResponseDTO()

    init {
        listProductModel = listOf(
            ProductModel(
            cateId = productResponseMock.cateId,
            productId = productResponseMock.productId,
                salePrice = productResponseMock.salePrice,
                quantity = productResponseMock.quantity,importPrice = productResponseMock.importPrice,
                description = productResponseMock.description,
                productName = productResponseMock.productName,
                productImage = productResponseMock.productImage
        ))


    }


    override fun getAllProducts(productRequestDTO: GetAllProductRequestDTO):
            Flow<TaskResult<List<ProductModel>>> {
        return flow {
            emit(TaskResult.Loading)
            delay(2000L)
            if (isError) {
                emit(TaskResult.Failure(AppError.GeneralError(NullPointerException())))
            } else {
                val result = productResponseMock.results
                emit(TaskResult.Success(result))
            }
        }
    }

    override fun getProductsByCate(getProductByCateRequestDTO: GetProductByCateRequestDTO):
            Flow<TaskResult<List<ProductModel>>> {
        return flow {
            emit(TaskResult.Loading)
            delay(2000L)
            if (isError) {
                emit(TaskResult.Failure(AppError.GeneralError(NullPointerException())))
            } else {
                val result = productResponseMock.results
                emit(TaskResult.Success(result))
            }
        }
    }

    override fun getProductsByName(getProductByNameRequestDTO: GetProductByNameRequestDTO): Flow<TaskResult<List<ProductModel>>> {
        return flow {
            emit(TaskResult.Loading)
            delay(2000L)
            if (isError) {
                emit(TaskResult.Failure(AppError.GeneralError(NullPointerException())))
            } else {
                val result = productResponseMock.results
                emit(TaskResult.Success(result))
            }
        }
    }

    override fun getProductsByCateAndName(getProductByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO): Flow<TaskResult<List<ProductModel>>> {
        return flow {
            emit(TaskResult.Loading)
            delay(2000L)
            if (isError) {
                emit(TaskResult.Failure(AppError.GeneralError(NullPointerException())))
            } else {
                val result = productResponseMock.results
                emit(TaskResult.Success(result))
            }
        }
    }

    override fun updateProductById(
        productId: Int,
        salePrice: Int,
        quantity: Int,
        description: String,
        productName: String
    ): Flow<TaskResult<UpdateProductByIdModel>> {
        TODO("Not yet implemented")
    }

    override fun addProduct(
        cateId: Int,
        importPrice: Int,
        quantity: Int,
        description: String,
        productName: String,
        productImage: String,
        salePrice: Int
    ): Flow<TaskResult<AddProductModel>> {
        TODO("Not yet implemented")
    }

}
