package com.example.big_nerd_ranch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders.of
import androidx.lifecycle.ViewModelStores.of
import com.example.big_nerd_ranch.model.Question
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.EnumSet.of
import java.util.List.of

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private  val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.curretIndex = currentIndex
        // state interface
//        val quizViewModel: QuizViewModel by viewModels()
//        Log.d(TAG,"Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById((R.id.cheat_button))
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
            quizViewModel.moveToNext()
//            curretIndex = (curretIndex + 1) % questionBank.size
            updateQuestion()
        }
        prevButton.setOnClickListener {
//            curretIndex = (curretIndex - 1 + questionBank.size) % questionBank.size
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        cheatButton.setOnClickListener {
//           val intent = Intent(this, CheatActivity::class.java)
        val answerIsTrue = quizViewModel.currentQuestionAnswer
        val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
//            startActivity(intent)
            // 6.13
            startActivityForResult(intent,REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }
    // сохранение данных при засыпании приложения
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX,quizViewModel.curretIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }
    private fun updateQuestion() {
//        val questionTextResId = questionBank[curretIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
//        val correctAnswer = questionBank[curretIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else {
            R.string.incorrect_toast
        }
        val myToast=Toast.makeText(this,messageResId, Toast.LENGTH_SHORT)
            myToast.setGravity(Gravity.TOP,0,0)
            myToast.show()
            Snackbar.make(activity_main_container, messageResId, 3000).setAction(getString(R.string.snack_bar_message)) {
            }.show()
    }
}