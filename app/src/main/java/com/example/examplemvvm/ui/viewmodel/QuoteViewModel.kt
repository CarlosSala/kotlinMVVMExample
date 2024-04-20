package com.example.examplemvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examplemvvm.data.model.QuoteModel
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

    // livedata permite a la activity suscribirse a un modelo de datos nuestro
    // que se llama automaticamente cuando se produce un cambio en dicho modelo

    val quoteModel = MutableLiveData<Quote?>()
    val isLoading = MutableLiveData<Boolean>()

    // it is no  necessary with dependency injection
    /*  var getQuoteUseCase = GetQuoteUseCase()
        var getRandomQuoteUseCase = GetRandomQuoteUseCase()*/

    // for load a quote to the start app
    fun onCreate() {

        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getQuoteUseCase()
            if (result.isNotEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)
            }
        }

    }

    // for change quote when user touch screen
    fun randomQuote() {

        viewModelScope.launch {

            isLoading.postValue(true)

            val quote = getRandomQuoteUseCase()
            if (quote != null) {
                quoteModel.postValue(quote)
            }
            /*
                    val currentQuote: QuoteModel = QuoteProvider.random()
                    quoteModel.postValue(currentQuote)*/
            isLoading.postValue(false)
        }
    }
}