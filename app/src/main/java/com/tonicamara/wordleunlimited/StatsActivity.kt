package com.tonicamara.wordleunlimited

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.text.DecimalFormat

class StatsActivity : AppCompatActivity() {
    private val myMethods = MyMethods()

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
            if (df.format(data.averageTries).toString() != "0"){
                averageTries.text = df.format(data.averageTries).toString()
            } else averageTries.text = "-"
            timesGivenUp.text = data.timesGivenUp.toString()
            timesWon.text = data.timesWon.toString()
        }

        val resetStatsButton = findViewById<View>(R.id.resetStatsBtn)
        resetStatsButton.setOnClickListener {
            resetStatsDialog(statsManager, this,this)


        }
    }

    private fun resetStatsDialog(statsManager: StatsManager, context: Context, StatsActivity: StatsActivity) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Esta acción borrará todos los datos de tus partidas.\n\nDeseas continuar?")

            .setNegativeButton("Cancelar") { _, _ ->
                // Respond to positive button press
                myMethods.vibratePhone(this)
            }
            .setPositiveButton("Borrar Estadísticas") { _, _ ->
                myMethods.vibratePhone(this)
                val userStats = UserStats()
                statsManager.storeStats(userStats)

                val intent = Intent(this, StatsActivity::class.java)
                StatsActivity.startActivity(intent)
                StatsActivity.finish()
            }
            .setCancelable(false)
            .show()
    }

}


