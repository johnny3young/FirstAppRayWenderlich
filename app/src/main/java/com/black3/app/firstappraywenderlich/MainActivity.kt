package com.black3.app.firstappraywenderlich

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var score = 0
    var gameStarted = false
    lateinit var countDownTimer: CountDownTimer
    val initialCountDown: Long = 60000
    val countDownInterval: Long = 1000
    var timeLeftOnTimer: Long = 60000

    companion object{
        private val TAG = MainActivity::class.java.simpleName
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreated called. Score is $score")

        if (savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else{
            ResetGame()
        }

        btn_tapme.setOnClickListener {
            incrementScore()
        }

    }

    private fun restoreGame() {
        tv_Score.text = getString(R.string.your_score,score)
        val restoredTime = timeLeftOnTimer / 1000
        tv_time.text = getString(R.string.time,restoredTime)

        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                tv_time.text = getString(R.string.time,timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()
        Log.d(TAG,"onSavedInstanceState: Saving Score: $score & Time: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy called")
    }

    private fun ResetGame() {
        //Este if nos serviria para iniciar el contador PERO si tuvieramos que agregar otro botón al proyecto ya no serviria
        /*if (!gameStarted){
            countDownTimer.start()
            gameStarted = true
        }*/
        score = 0
        tv_Score.text = getString(R.string.your_score, score)
        val initialTime = initialCountDown / 1000
        tv_time.text = getString(R.string.time, initialTime)

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                tv_time.text = getString(R.string.time, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }

    private fun incrementScore() {
        if (!gameStarted){
            starGame()
        }
        score += 1
        val newScore = getString(R.string.your_score, score)
        tv_Score.text = newScore
    }

    private fun starGame(){
        countDownTimer.start()
        gameStarted = true
    }
    private fun endGame(){
        Toast.makeText(this,getString(R.string.gameOverMessage,score),Toast.LENGTH_LONG).show()
        ResetGame()
    }
}