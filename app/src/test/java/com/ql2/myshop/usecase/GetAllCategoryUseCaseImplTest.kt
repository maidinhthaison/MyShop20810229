package com.ql2.myshop.usecase

import com.ql2.myshop.data.usecase.category.GetAllCategoryUseCaseImpl
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.repository.category.CategoryRepository
import com.ql2.myshop.repo.CategoryRepositoryMock
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAllCategoryUseCaseImplTest {
    private lateinit var repository: CategoryRepository

    @Before
    fun init() {
        repository = CategoryRepositoryMock()
    }


    @Test
    fun `fetch list category response success`() {
        runBlocking {

            val useCase = GetAllCategoryUseCaseImpl(repository)
            val results = useCase.invoke().take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertEquals(results[1], TaskResult.Success(results[1].value()))
        }
    }
    @Test
    fun `fetch list category response fail`() {
        runBlocking {

            val useCase = GetAllCategoryUseCaseImpl(repository)
            val results = useCase.invoke().take(2).toList()
            Assert.assertEquals(results[0], TaskResult.Loading)
            Assert.assertNotEquals(results[1], results[1].error()?.let { TaskResult.Failure(it) })
        }
    }
}