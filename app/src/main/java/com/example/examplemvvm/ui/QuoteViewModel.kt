package com.example.examplemvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.domain.GetQuoteUseCase
import com.example.examplemvvm.domain.GetRandomQuoteUseCase
import com.example.examplemvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    private val _quoteModel = MutableStateFlow<Quote?>(null)
    val quoteModel: StateFlow<Quote?> = _quoteModel

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // it is no  necessary with dependency injection
    /*  var getQuoteUseCase = GetQuoteUseCase()
        var getRandomQuoteUseCase = GetRandomQuoteUseCase()*/

    // for load a quote to the start app
    fun onCreate() {

        viewModelScope.launch {
            _isLoading.value = true
            val result = getQuoteUseCase()
            if (result.isNotEmpty()) {
                _quoteModel.value = result[0]
                _isLoading.value = false
            }
        }
    }

    // for change quote when user touch screen
    fun randomQuote() {

        viewModelScope.launch {
            _isLoading.value = true
            val quote = getRandomQuoteUseCase()
            if (quote != null) {
                _quoteModel.value = quote
            }
            /*
               val currentQuote: QuoteModel = QuoteProvider.random()
               quoteModel.postValue(currentQuote)*/
            _isLoading.value = false
        }
    }
}