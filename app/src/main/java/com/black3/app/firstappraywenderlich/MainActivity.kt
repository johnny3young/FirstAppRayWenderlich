package com.black3.app.firstappraywenderlich

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMeButton : Button
    internal lateinit var scoreTv : TextView
    internal var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById(R.id.btn_tapme)
        scoreTv = findViewById(R.id.tv_Score)

        tapMeButton.setOnClickListener {
            incrementScore()
        }

    }

    private fun incrementScore() {
        score += 1
        val newScore = getString(R.string.your_score, score)
        scoreTv.text = newScore
    }
}