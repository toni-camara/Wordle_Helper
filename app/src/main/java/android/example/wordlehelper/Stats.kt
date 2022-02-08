package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import java.io.File
import java.text.DecimalFormat


class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val gamesPlayed = findViewById<TextView>(R.id.gamesPlayedView)
        val averageTries = findViewById<TextView>(R.id.averageTriesView)
        val timesGivenUp = findViewById<TextView>(R.id.timesGivenUpView)
        val timesWon = findViewById<TextView>(R.id.timesWonView)

        val statsFile = File(this.filesDir,"statsFile.json")
        if(!statsFile.exists()) {
            gamesPlayed.text = "0"
            averageTries.text = "N/A"
            timesGivenUp.text = "0"
            timesWon.text = "0"
        }

        else{
            val data = readStatsFile(statsFile)
            val df = DecimalFormat("##.#")
            gamesPlayed.text = data.timesPlayed.toString()
            averageTries.text = df.format(data.averageTries).toString()
            timesGivenUp.text = data.timesGivenUp.toString()
            timesWon.text = data.timesWon.toString()
        }


    }

    fun readStatsFile(File: File): UserStats {
        val string: String = File.readText()
        return Gson().fromJson(string, UserStats::class.java)
    }

    fun writeStatsFile(File: File, data: UserStats){
        val gson = Gson()
        val jsonStats: String = gson.toJson(data)
        File.writeText(jsonStats)
    }
}


data class UserStats(
    var timesPlayed: Int = 0,
    var averageTries: Float? = 6.0f,
    var timesGivenUp: Int = 0,
    var timesWon: Int = 0
)