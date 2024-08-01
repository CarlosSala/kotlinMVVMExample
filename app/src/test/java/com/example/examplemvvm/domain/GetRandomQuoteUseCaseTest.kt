package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetRandomQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }

    @Test
    fun `when the dataBase return a empty list`() = runBlocking {

        // Given
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // When
        val result = getRandomQuoteUseCase()

        // Then
        assert(result == null)
    }

    @Test
    fun `when the dataBase is not empty return a quote`() = runBlocking {

        // Given
        val quoteList = listOf(Quote("my quote", "carlos"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // When
        val result = getRandomQuoteUseCase()

        // Then
        assert(result == quoteList.first())

    }

}