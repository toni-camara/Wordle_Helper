package android.example.wordlehelper

import android.content.Context
import android.widget.LinearLayout
import com.google.gson.Gson
import java.io.File

class StatsManager(context: Context) {
    private val file = File(context.filesDir, USER_STATS_FILE_NAME)

    fun readStatsFromFile(file: File): UserStats {
        val string: String = file.readText()
        return Gson().fromJson(string, UserStats::class.java)
    }

    private fun writeStatsFile(File: File, data: UserStats) {
        val gson = Gson()
        val jsonStats: String = gson.toJson(data)
        File.writeText(jsonStats)
    }

    private fun getStats(): UserStats? {
        if (!file.exists()) {
            return null
        }

        val userStatsString: String = file.readText()
        return Gson().fromJson(userStatsString, UserStats::class.java)
    }

    private fun storeStats(data: UserStats) {
        val userStatsJson: String = Gson().toJson(data)
        file.writeText(userStatsJson)
    }

    fun updateStatsGiveUp() {
        val userStats = getStats() ?: UserStats()
        userStats.timesPlayed++
        userStats.timesGivenUp++
        storeStats(userStats)
    }

    fun updateStatsGameFinished(context: Context, attempt: LinearLayout, guessWordsLayout: LinearLayout, victory: Boolean) {
        val statsFile = File(context.filesDir, "statsFile.json")
        if (statsFile.exists()) {
            val stats = readStatsFromFile(statsFile)
            stats.averageTries =
                (((stats.timesPlayed * stats.averageTries) + (guessWordsLayout.indexOfChild(attempt) + 1)) / (stats.timesPlayed + 1))
            stats.timesPlayed++
            if (victory) stats.timesWon++
            writeStatsFile(statsFile, stats)
        } else {
            var timesWon: Int
            if (!victory) {
                timesWon = 0
            } else timesWon = 1
            val averageTries = (guessWordsLayout.indexOfChild(attempt) + 1).toFloat()
            val data = UserStats(1, averageTries, 0, timesWon)
            writeStatsFile(statsFile, data)
        }
    }


}

private const val USER_STATS_FILE_NAME = "statsFile.json"

data class UserStats(
    var timesPlayed: Int = 0,
    var averageTries: Float = 6.0f,
    var timesGivenUp: Int = 0,
    var timesWon: Int = 0
)