package com.example.examplemvvm.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.examplemvvm.databinding.ActivityMainBinding
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

        quoteViewModel.quoteModel.observe(this@MainActivity) { currentQuote ->

            binding.apply {
                tvQuote.text = currentQuote?.quote ?: "there is not quote"
                tvAuthor.text = currentQuote?.author ?: "there is not author"
            }
        }

        quoteViewModel.isLoading.observe(this) {
            binding.pb.isVisible = it
        }

        binding.clViewContainer.setOnClickListener { quoteViewModel.randomQuote() }
    }
}