package com.tonicamara.wordleunlimited

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.text.DecimalFormat

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val statsManager = StatsManager(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val gamesPlayed = findViewById<TextView>(R.id.gamesPlayedView)
        val averageTries = findViewById<TextView>(R.id.averageTriesView)
        val timesGivenUp = findViewById<TextView>(R.id.timesGivenUpView)
        val timesWon = findViewById<TextView>(R.id.timesWonView)

        val statsFile = File(this.filesDir, "statsFile.json")
        if (!statsFile.exists()) {
            gamesPlayed.text = "0"
            averageTries.text = "N/A"
            timesGivenUp.text = "0"
            timesWon.text = "0"
        } else {
            val data = statsManager.readStatsFromFile(statsFile)
            val df = DecimalFormat("##.#")
            gamesPlayed.text = data.timesPlayed.toString()
            averageTries.text = df.format(data.averageTries).toString()
            timesGivenUp.text = data.timesGivenUp.toString()
            timesWon.text = data.timesWon.toString()
        }
    }

}


