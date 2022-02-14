package com.tonicamara.wordleunlimited

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    private val myMethods = MyMethods()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**PLAY BUTTON*/
        val playButton = findViewById<Button>(R.id.playBtn)
        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            myMethods.vibratePhone(this)
        }

        /**LEARN BUTTON*/
        val learnButton = findViewById<Button>(R.id.learnBtn)
        learnButton.setOnClickListener {
            val intent = Intent(this, Learn::class.java)
            startActivity(intent)
            myMethods.vibratePhone(this)
        }

        /**STATS BUTTON*/
        val statsButton = findViewById<Button>(R.id.statsBtn)
        statsButton.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
            myMethods.vibratePhone(this)
        }

        /**CODE BUTTON*/
        val codeButton = findViewById<Button>(R.id.codeBtn)
        codeButton.setOnClickListener {
            val intent = Intent(this, CodeActivity::class.java)
            startActivity(intent)
            myMethods.vibratePhone(this)
        }

    }
}

