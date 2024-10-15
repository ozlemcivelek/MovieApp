package com.example.movieapp.example

import android.util.Log
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    init {
        Log.d("TAG", "viewModel: initialized")
    }

    var counter = 0

    fun incrementCounter() {
        counter++
    }
}