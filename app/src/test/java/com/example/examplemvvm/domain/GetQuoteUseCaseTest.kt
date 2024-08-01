package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.domain.model.Quote
import org.junit.Before
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.math.exp


class GetQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    private lateinit var getQuoteUseCase: GetQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getQuoteUseCase = GetQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when the api does not return anything then get values from database`() = runBlocking {

        // Given
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()

        // When
        getQuoteUseCase()

        // Then
        coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDatabase() }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {

        // Given
        val myList = listOf(Quote("my quote", "carlos"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList

        // When
        val result = getQuoteUseCase()

        // Then
        coVerify(exactly = 1) { quoteRepository.clearQuotes() }
        coVerify(exactly = 1) { quoteRepository.insertQuotes(any()) }
        coVerify(exactly = 0) { quoteRepository.getAllQuotesFromDatabase() }
        assert(myList == result)
    }
}