package com.ql2.myshop.usecase

import com.ql2.myshop.data.usecase.category.GetAllCategoryUseCaseImpl
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

    @Before
    fun init() {
        repository = ProductRepositoryMock()
    }


    @Test
    fun `fetch list product response success`() {
        runBlocking {

            val useCase = GetProductUseCaseImpl(repository)
            val results = useCase.invoke().take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertEquals(results[1], TaskResult.Success(results[1].value()))
        }
    }
    @Test
    fun `fetch list product response fail`() {
        runBlocking {

            val useCase = GetProductUseCaseImpl(repository)
            val results = useCase.invoke().take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertNotEquals(results[1], results[1].error()?.let { TaskResult.Failure(it) })
        }
    }
}