package com.example.big_nerd_ranch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.big_nerd_ranch.model.Question

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_africa,false),
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_meadeast,false)
    )
    private var curretIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
//            Toast.makeText(this,R.string.correct_toast,Toast.LENGTH_SHORT).show()
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
//            Toast.makeText(this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show()
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            curretIndex = (curretIndex + 1) % questionBank.size
            updateQuestion()
        }

        prevButton.setOnClickListener {
            curretIndex = (curretIndex - 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[curretIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[curretIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else {
            R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }
}