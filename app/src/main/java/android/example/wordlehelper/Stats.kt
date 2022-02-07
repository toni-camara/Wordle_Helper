package android.example.wordlehelper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.IOException


class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        var gamesPlayed = findViewById<TextView>(R.id.gamesPlayedView)
        var averageTries = findViewById<TextView>(R.id.averageTriesView)
        var timesGivenUp = findViewById<TextView>(R.id.timesGivenUpView)

        val statsFile = File(this.filesDir,"statsFile.json")
        if(!statsFile.exists()) writeStatsFile(statsFile)

        else{
            val data = readStatsFile(statsFile)
            gamesPlayed.text = data.timesPlayed.toString()
            averageTries.text = data.averageTries.toString()
            timesGivenUp.text = data.timesGivenUp.toString()
        }


    }

    fun readStatsFile(File: File): UserStats{
        var string: String
        string = File.readText()
        println("Tu cadena es $string")
        val dataBuffer = Gson().fromJson<UserStats>(string, UserStats::class.java)
        return dataBuffer
    }

    fun writeStatsFile(File: File){
        var data: UserStats = UserStats()
        val gson = Gson()
        val jsonStats: String = gson.toJson(data)
        File.writeText(jsonStats)
    }
}


data class UserStats(
    var timesPlayed: Int = 0,
    var averageTries: Float? = 3.0f,
    var timesGivenUp: Int = 0
)