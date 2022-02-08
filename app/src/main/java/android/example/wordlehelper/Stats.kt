package android.example.wordlehelper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.text.DecimalFormat


class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        var gamesPlayed = findViewById<TextView>(R.id.gamesPlayedView)
        var averageTries = findViewById<TextView>(R.id.averageTriesView)
        var timesGivenUp = findViewById<TextView>(R.id.timesGivenUpView)
        var timesWon = findViewById<TextView>(R.id.timesWonView)

        val statsFile = File(this.filesDir,"statsFile.json")
        if(!statsFile.exists()) {
            gamesPlayed.text = "0"
            averageTries.text = "N/A"
            timesGivenUp.text = "0"
            timesWon.text = "0"
        }

        else{
            val data = readStatsFile(statsFile)
            val df: DecimalFormat = DecimalFormat("##.#")
            gamesPlayed.text = data.timesPlayed.toString()
            averageTries.text = df.format(data.averageTries).toString()
            timesGivenUp.text = data.timesGivenUp.toString()
            timesWon.text = data.timesWon.toString()
        }


    }

    fun readStatsFile(File: File): UserStats{
        var string: String
        string = File.readText()
        val dataBuffer = Gson().fromJson<UserStats>(string, UserStats::class.java)
        return dataBuffer
    }

    fun writeStatsFile(File: File, data: UserStats){
        val gson = Gson()
        val jsonStats: String = gson.toJson(data)
        File.writeText(jsonStats)
    }
}


public data class UserStats(
    var timesPlayed: Int = 0,
    var averageTries: Float? = 6.0f,
    var timesGivenUp: Int = 0,
    var timesWon: Int = 0
)