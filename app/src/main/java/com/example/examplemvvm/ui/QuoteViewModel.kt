package com.example.examplemvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.domain.GetQuoteUseCase
import com.example.examplemvvm.domain.GetRandomQuoteUseCase
import com.example.examplemvvm.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    private val _quoteModel = MutableLiveData<Quote?>()
    val quoteModel: MutableLiveData<Quote?> = _quoteModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    // it is no  necessary with dependency injection
    /*  var getQuoteUseCase = GetQuoteUseCase()
        var getRandomQuoteUseCase = GetRandomQuoteUseCase()*/

    // for load a quote to the start app
    fun onCreate() {

        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = getQuoteUseCase()
            if (result.isNotEmpty()) {
                _quoteModel.postValue(result[0])
                _isLoading.postValue(false)
            }
        }
    }

    // for change quote when user touch screen
    fun randomQuote() {

        viewModelScope.launch {
            _isLoading.postValue(true)
            val quote = getRandomQuoteUseCase()
            if (quote != null) {
                _quoteModel.postValue(quote)
            }
            /*
               val currentQuote: QuoteModel = QuoteProvider.random()
               quoteModel.postValue(currentQuote)*/
            _isLoading.postValue(false)
        }
    }
}