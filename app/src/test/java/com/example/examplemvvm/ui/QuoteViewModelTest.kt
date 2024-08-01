package com.example.examplemvvm.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.examplemvvm.domain.GetQuoteUseCase
import com.example.examplemvvm.domain.GetRandomQuoteUseCase
import com.example.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuoteViewModelTest {

    @RelaxedMockK
    private lateinit var getQuoteUseCase: GetQuoteUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuoteUseCase, getRandomQuoteUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewModel is  created at the first time, get all quotes and set the first value`() =
        runTest {

            // Given
            val quoteList = listOf(
                Quote("my quote", "carlos"),
                Quote("my quote", "carlos")
            )

            coEvery { getQuoteUseCase() } returns quoteList

            // when
            quoteViewModel.onCreate()

            // then
            assert(quoteViewModel.quoteModel.value == quoteList.first())
        }

    @Test
    fun `when randomQuoteUseCase returns a quote set on the livedata`() = runTest {

        // Given
        val quote = Quote("my quote", "carlos")

        coEvery { getRandomQuoteUseCase() } returns quote

        // when
        quoteViewModel.randomQuote()

        // then
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `if randomQuoteUseCase return null keep the last value`() = runTest {

        // Given
        val quote = Quote("my quote", "carlos")
        quoteViewModel.quoteModel.value = quote
        coEvery { getRandomQuoteUseCase() } returns null

        // when
        quoteViewModel.randomQuote()

        // then
        assert(quoteViewModel.quoteModel.value == quote)
    }
}