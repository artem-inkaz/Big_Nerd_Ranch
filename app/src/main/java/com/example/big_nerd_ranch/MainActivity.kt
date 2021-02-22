package com.example.big_nerd_ranch

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.big_nerd_ranch.CheatActivity.Companion.newIntent
import com.example.big_nerd_ranch.CrimeIntent.FragmentCriminalIntent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0
//private const val EXTRA_ANSWER_SHOWN = "answer_is_shown"
//private const val IS_CHEATER = "isCheater"
class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView
    private val quizViewModel: QuizViewModel by viewModels()
    //----------------------------------------------------
    private lateinit var criminalIntentButton: Button
    //val fragmentCriminalIntent = FragmentCriminalIntent()

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("RestrictApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        // 1 to 7 Chapter
        GeoQuiz(savedInstanceState)
        //-----------------------------------------------------------------------
        // 8 Chapter goto new Activity FragmentCriminalIntent
          criminalIntentButton = findViewById(R.id.btn_criminal_intent)
          criminalIntentButton.setOnClickListener {
              // Variant 1
                val i = Intent(this,FragmentCriminalIntent::class.java).apply { }
              startActivity(i)
              // Variant 2
//              val intent2 = newIntent(this@MainActivity, false)
//              startActivity(intent2)
            }
    }

    private fun GeoQuiz(savedInstanceState: Bundle?) {
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
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
            quizViewModel.isCheater = false
            quizViewModel.moveToNext()
    //            curretIndex = (curretIndex + 1) % questionBank.size
            updateQuestion()
        }
        prevButton.setOnClickListener {
    //            curretIndex = (curretIndex - 1 + questionBank.size) % questionBank.size
            quizViewModel.moveToPrevious()
            updateQuestion()
        }
        var tokens = 3
        cheatButton.setOnClickListener {
    //           val intent = Intent(this, CheatActivity::class.java)
            if (tokens <= 3 && tokens != 0) {  // Can use also (!(tokens > 3 || tokens == 0))
                tokens -= 1
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = newIntent(this@MainActivity, answerIsTrue)
    //            startActivity(intent)
                // 6.13
                // startActivityForResult(intent,REQUEST_CODE_CHEAT)
                // 7.1 Animation
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val options = ActivityOptions
                            .makeClipRevealAnimation(cheatButton, 0, 0, cheatButton.width, cheatButton.width)
                    startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
                } else {
                    startActivityForResult(intent, REQUEST_CODE_CHEAT)
                }
            } else {
                cheatButton.isEnabled = false
                //makeText(this, "Oh well, you are out of cheats", LENGTH_SHORT).show()
                Snackbar.make(activity_main_container, "Oh well, you are out of cheats", 3000).setAction(getString(R.string.snack_bar_message)) {
                }.show()
            }
        }
        //    }
        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK){
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
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
    // сохранение данных при засыпании приложения 1 to 7 Chapter
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
    // 1 to 7 Chapter
    private fun updateQuestion() {
//        val questionTextResId = questionBank[curretIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }
    // 1 to 7 Chapter
    private fun checkAnswer(userAnswer: Boolean){
//        val correctAnswer = questionBank[curretIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer

//        val messageResId = if (userAnswer == correctAnswer){
//            R.string.correct_toast
//        }else {
//            R.string.incorrect_toast
//        }

        val messageResId = when{
            quizViewModel.isCheater -> R.string.judment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        val myToast=Toast.makeText(this,messageResId, Toast.LENGTH_SHORT)
            myToast.setGravity(Gravity.TOP,0,0)
            myToast.show()
            Snackbar.make(activity_main_container, messageResId, 3000).setAction(getString(R.string.snack_bar_message)) {
            }.show()
    }
}


