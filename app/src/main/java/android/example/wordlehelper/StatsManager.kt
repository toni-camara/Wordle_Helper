package android.example.wordlehelper

import android.content.Context
import com.google.gson.Gson
import java.io.File

class StatsManager(context: Context) {
    private val file = File(context.filesDir, USER_STATS_FILE_NAME)

    fun doesStatsFileExists(): Boolean {
        return file.exists()
    }

    fun getStats(): UserStats? {
        if (!file.exists()) {
            return null
        }

        val userStatsString: String = file.readText()
        return Gson().fromJson(userStatsString, UserStats::class.java)
    }

    fun storeStats(data: UserStats) {
        val userStatsJson: String = Gson().toJson(data)
        file.writeText(userStatsJson)
    }
}

private const val USER_STATS_FILE_NAME = "statsFile.json"

data class UserStats(
    var timesPlayed: Int = 0,
    var averageTries: Float? = 6.0f,
    var timesGivenUp: Int = 0,
    var timesWon: Int = 0
)