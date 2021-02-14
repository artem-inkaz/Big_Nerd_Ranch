package com.example.big_nerd_ranch

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "MainActivity"

class QuizViewModel:ViewModel() {

    init {
        Log.d(TAG,"ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG,"ViewModel instance about to be destroyed")
    }
}