package com.example.examplemvvm.domain

import com.example.examplemvvm.data.QuoteRepository
import com.example.examplemvvm.data.database.entities.toDataBase
import com.example.examplemvvm.domain.model.Quote
import javax.inject.Inject

class GetQuoteUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    // dagger hilt
    // private val repository = QuoteRepository()

    /*   suspend operator fun invoke(): List<QuoteModel> {
           return repository.getAllQuotes()
       }*/

    suspend operator fun invoke(): List<Quote> {

        val quotes = repository.getAllQuotesFromApi()

        return if (quotes.isNotEmpty()) {

            repository.clearQuotes()

            // insert in database
            repository.insertQuotes(quotes.map { it.toDataBase() })
            quotes
        } else {
            repository.getAllQuotesFromDatabase()
        }
    }
}