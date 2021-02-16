package com.example.big_nerd_ranch

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.big_nerd_ranch.model.Question

private const val TAG = "MainActivity"

class QuizViewModel: ViewModel() {

    var curretIndex = 0
    // отслеживание подглядывания
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_africa,false),
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_meadeast,false)
    )
    // вычисляемые поля, чтобы вернуть текст и ответ на текущий вопрос
    val currentQuestionAnswer: Boolean
    get() = questionBank[curretIndex].answer

    val currentQuestionText: Int
    get() = questionBank[curretIndex].textResId

    fun moveToNext(){
        curretIndex = (curretIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        curretIndex = (curretIndex - 1 + questionBank.size) % questionBank.size
    }

}