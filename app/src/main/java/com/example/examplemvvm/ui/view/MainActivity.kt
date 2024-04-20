package com.example.examplemvvm.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.examplemvvm.databinding.ActivityMainBinding
import com.example.examplemvvm.ui.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quoteViewModel.onCreate()

        // todo lo que esta dentro esta enganchado a livedata y ese ejecutara acá
        quoteViewModel.quoteModel.observe(this@MainActivity, Observer { currentQuote ->

            binding.apply {

                tvQuote.text = currentQuote?.quote ?: "there is not quote"
                tvAutor.text = currentQuote?.author ?: "there is not author"
            }
        })

        quoteViewModel.isLoading.observe(this, Observer {
            binding.pb.isVisible = it
        })

        binding.clViewContainer.setOnClickListener { quoteViewModel.randomQuote() }
    }
}