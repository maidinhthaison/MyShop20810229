package com.ql2.myshop.usecase

import com.ql2.myshop.data.api.request.GetAllProductRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.data.usecase.category.GetAllCategoryUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductByCateAndNameUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductByCateUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductByNameUseCaseImpl
import com.ql2.myshop.data.usecase.product.GetProductUseCaseImpl
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.repo.CategoryRepositoryMock
import com.ql2.myshop.repo.ProductRepositoryMock
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetProductUseCaseImplTest {
    private lateinit var repository: ProductRepository
    private lateinit var getAllProductsRequestDTO: GetAllProductRequestDTO
    private lateinit var etProductsByCateRequestDTO: GetProductByCateRequestDTO
    private lateinit var getProductsByNameRequestDTO: GetProductByNameRequestDTO
    private lateinit var getProductsByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO
    @Before
    fun init() {
        repository = ProductRepositoryMock()
        getAllProductsRequestDTO = GetAllProductRequestDTO(limit = 10, offset = 0)
        etProductsByCateRequestDTO = GetProductByCateRequestDTO(cateId = 3, limit = 10, offset = 0)
        getProductsByNameRequestDTO =
            GetProductByNameRequestDTO(proName = "mini", limit = 10, offset = 0)
        getProductsByCateAndNameRequestDTO =
            GetProductByCateAndNameRequestDTO(cateId = 3, proName = "mini", limit = 10, offset = 0)
    }


    @Test
    fun `fetch getAllProducts response success`() {
        runBlocking {
            val useCase = GetProductUseCaseImpl(repository)
            val results = useCase(getAllProductsRequestDTO).take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertEquals(results[1], TaskResult.Success(results[1].value()))
        }
    }

    @Test
    fun `fetch getProductsByCate response success`() {
        runBlocking {
            val useCase = GetProductByCateUseCaseImpl(repository)
            val results = useCase(etProductsByCateRequestDTO).take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertEquals(results[1], TaskResult.Success(results[1].value()))
        }
    }

    @Test
    fun `fetch getProductsByName response success`() {
        runBlocking {
            val useCase = GetProductByNameUseCaseImpl(repository)
            val results = useCase(getProductsByNameRequestDTO).take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertEquals(results[1], TaskResult.Success(results[1].value()))
        }
    }

    @Test
    fun `fetch getProductsByCateAndName response success`() {
        runBlocking {
            val useCase = GetProductByCateAndNameUseCaseImpl(repository)
            val results = useCase(getProductsByCateAndNameRequestDTO).take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertEquals(results[1], TaskResult.Success(results[1].value()))
        }
    }

    @Test
    fun `fetch list product response fail`() {
        runBlocking {

            val useCase = GetProductUseCaseImpl(repository)
            val results = useCase(getAllProductsRequestDTO).take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertNotEquals(results[1], results[1].error()?.let { TaskResult.Failure(it) })
        }
    }
}