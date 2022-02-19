package com.tonicamara.wordleunlimited

import android.content.Context
import com.google.gson.Gson
import java.io.File

class StatsManager(context: Context) {
    private val file = File(context.filesDir, USER_STATS_FILE_NAME)

    fun readStatsFromFile(file: File): UserStats {
        val string: String = file.readText()
        return Gson().fromJson(string, UserStats::class.java)
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
        userStats.averageTries = ((userStats.timesPlayed * userStats.averageTries) + 6) / (userStats.timesPlayed + 1)
        userStats.timesPlayed++
        userStats.timesGivenUp++
        storeStats(userStats)
    }

    fun updateStatsGameFinished(tries: Int, isVictory: Boolean) {
        val userStats = getStats() ?: UserStats()
        if (isVictory) {
            userStats.timesWon++
        }

        userStats.averageTries = ((userStats.timesPlayed * userStats.averageTries) + tries) / (userStats.timesPlayed + 1)
        userStats.timesPlayed++
        storeStats(userStats)
    }

}

private const val USER_STATS_FILE_NAME = "statsFile.json"

data class UserStats(
    var timesPlayed: Int = 0,
    var averageTries: Float = 6.0f,
    var timesGivenUp: Int = 0,
    var timesWon: Int = 0
)