package com.example.big_nerd_ranch

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.big_nerd_ranch.CrimeIntent.CrimeListViewModel
import kotlinx.android.synthetic.main.activity_cheat.*

private const val EXTRA_ANSWER_IS_TRUE =
    "com.example.big_nerd_ranch.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.example.big_nerd_ranch.answer_shown"
private const val TAG = "CheatActivity"
private const val KEY_WAS_CHEATED = "was_cheated"
class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    private var wasCheated = false
    private lateinit var answerTextView: TextView
    private lateinit var tVApiLevel: TextView
    private lateinit var showAnswerButton: Button

    private  val quizViewModel: QuizViewModel by viewModels()
//    private val quizViewModel: QuizViewModel by lazy {
//    ViewModelProviders.of(this).get(QuizViewModel::class.java)
//}
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG,"onSaveInstanceState")
        // Cheated
        outState.putBoolean(KEY_WAS_CHEATED, wasCheated)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        // Closing Loopholes for Cheaters
        wasCheated = savedInstanceState?.getBoolean(KEY_WAS_CHEATED, false) ?: false
        setAnswerShownResult(wasCheated)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        tVApiLevel = findViewById(R.id.tVApiLevel)
        tVApiLevel.text =getString(R.string.text_api_level) + Build.VERSION.SDK_INT.toString()


        showAnswerButton.setOnClickListener {
            // Closing Loopholes for Cheaters
            wasCheated = true
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            // 6.14 callback intent
            setAnswerShownResult(true)
        }
        // Closing Loopholes for Cheaters
        fillTextIfCheated()
    }
        // Closing Loopholes for Cheaters
    private fun fillTextIfCheated() {
        if (wasCheated) {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
        }
    }
    // присвоение результата интенту
    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)

    }
    companion object {
        fun newIntent(packageContext: Context,answerIsTrue: Boolean): Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue)
            }
        }
    }
}