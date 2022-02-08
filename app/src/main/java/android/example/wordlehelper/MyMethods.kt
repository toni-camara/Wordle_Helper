@file:Suppress("DEPRECATION")

package android.example.wordlehelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException


class MyMethods {


    /** This method takes a list of words from a file and stores them into a list */
    fun readWordsFromFile(context: Context): List<String> {
        var string = ""
        try {
            val inputStream = context.assets.open("example.txt")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer).toString()
            string = String(buffer)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        val list = string.split("\n")
        return list.sorted()

    }

    /** A letter button has been pressed*/
    fun letterPress(currentFocus: TextView, keyboardButton: TextView) {

        if (keyboardButton.text != "ENTER" && keyboardButton.text != "DEL") {
            if (currentFocus.text.toString() == "") {
                currentFocus.text = keyboardButton.text
            }
        }
    }


    /** Delete button has been pressed*/
    fun deletePress(currentFocus: TextView, keyboardButton: TextView) {

        if (keyboardButton.text == "DEL") {
            val parent = currentFocus.parent as LinearLayout
            val activeLetterIndex = parent.indexOfChild(currentFocus)

            if (activeLetterIndex != 0) {
                val nextActiveLetter =
                    parent.getChildAt(activeLetterIndex - 1) as TextView

                if (activeLetterIndex != 4) {
                    nextActiveLetter.text = ""
                    nextActiveLetter.requestFocus()
                } else {
                    if (currentFocus.text.toString() != "") {
                        currentFocus.text = ""
                    } else {
                        nextActiveLetter.text = ""
                        nextActiveLetter.requestFocus()
                    }
                }
            }
        }
    }


    /** Enter button has been pressed*/
    fun enterPress(
        currentFocus: TextView,
        keyboardButton: TextView,
        wordList: MutableList<String>,
        goalWord: String,
        resources: Resources,
        keyboardLayout: LinearLayout,
        context: Context,
        game: Activity
    ) {
        if (keyboardButton.text.toString() == "ENTER") {
            val parent = currentFocus.parent as LinearLayout
            val activeLetterIndex = parent.indexOfChild(currentFocus)

            /** Recoger palabra de las letras en la fila*/
            val guessedWordLetters = mutableListOf<String>()

            if (activeLetterIndex == 4) {
                for (letterPosition in 0 until parent.childCount) {
                    val currentLetter = parent.getChildAt(letterPosition) as TextView
                    guessedWordLetters.add(currentLetter.text.toString().lowercase())
                }

                val guessedWord = guessedWordLetters.joinToString("")

                /** Comprobar que la palabra introducida es valida*/
                if (!wordList.contains(guessedWord)) {
                    val text = "La palabra no es válida"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(game, text, duration)
                    toast.setGravity(Gravity.CENTER, 0, 130)
                    toast.show()
                } else {
                    validWord(
                        guessedWord,
                        goalWord,
                        parent,
                        resources,
                        keyboardLayout,
                        currentFocus,
                        context,
                        game
                    )
                }

            }
        }
    }

    /** Makes the phone vibrate for a short duration*/
    fun vibratePhone(context: Context) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(
            VibrationEffect.createOneShot(
                50,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    private fun validWord(
        guessedWord: String,
        goalWord: String,
        parent: LinearLayout,
        resources: Resources,
        keyboardLayout: LinearLayout,
        currentFocus: TextView,
        context: Context,
        game: Activity
    ) {

        val alreadyGreenList = mutableMapOf<Char, Boolean>()
        guessedWord.forEach { letter ->
            alreadyGreenList[letter] = false
        }

        val listOfColors = mutableListOf<Int>()
        val green = resources.getColor(R.color.verde)
        val yellow = resources.getColor(R.color.amarillo)
        val black = resources.getColor(R.color.negro)

        guessedWord.lowercase().forEachIndexed { index, letter ->
            val comparedLetter = parent.getChildAt(index) as TextView

            /** GREEN LETTER*/
            if (guessedWord[index] == goalWord[index]) {
                listOfColors.add(green)
                alreadyGreenList[letter] = true
                applyColorToLetter(comparedLetter, green, keyboardLayout)
            }

            /** YELLOW LETTER*/
            else if (goalWord.contains(letter) && !alreadyGreenList[letter]!!) {
                listOfColors.add(yellow)
                applyColorToLetter(comparedLetter, yellow, keyboardLayout)
            }

            /** BLACK LETTER*/
            else if (!goalWord.contains(letter) || alreadyGreenList[letter]!!) {
                listOfColors.add(black)
                applyColorToLetter(comparedLetter, black, keyboardLayout)
            }
        }

        /** SECOND PASS TO CORRECT INVALID YELLOWS*/
        guessedWord.lowercase().forEachIndexed { index, letter ->

            if (alreadyGreenList[letter]!! && listOfColors[index] == yellow) {
                val comparedLetter = parent.getChildAt(index) as TextView
                applyColorToLetter(comparedLetter, black, keyboardLayout)
            }
        }

        /** COMPARISON OF GUESSED WORD WITH GOALWORD*/
        val attempt = currentFocus.parent as LinearLayout
        val guessWordsLayout = attempt.parent as LinearLayout

        if (guessWordsLayout.indexOfChild(attempt) < 5) {
            var comparison = true
            for (i in 0 until attempt.childCount) {
                val comparedLetter = attempt.getChildAt(i) as TextView
                if (comparedLetter.text.toString()
                        .lowercase() != goalWord[i].toString()
                )
                    comparison = false
            }

            /** WORD GUESSED!*/
            if (comparison) {

                val messageText = "Enhorabuena! Has acertado!"
                showEndGameDialog(context, goalWord, game, messageText)

                //STATS FILE UPDATE
                val statsFile = File(context.filesDir, "statsFile.json")
                if (statsFile.exists()) {
                    val stats = Stats().readStatsFile(statsFile)
                    stats.averageTries =
                        (((stats.timesPlayed * stats.averageTries!!) + (guessWordsLayout.indexOfChild(
                            attempt
                        ) + 1)) / (stats.timesPlayed + 1))
                    stats.timesPlayed++
                    stats.timesWon++
                    Stats().writeStatsFile(statsFile, stats)
                } else {
                    val data = UserStats()
                    data.timesPlayed = 1
                    data.averageTries =
                        (guessWordsLayout.indexOfChild(attempt) + 1).toFloat()
                    data.timesGivenUp = 0
                    data.timesWon = 1
                    Stats().writeStatsFile(statsFile, data)
                }
            }

            /** YOU DIDN'T GUESS IT!*/
            else {
                val indexOfNextLine = guessWordsLayout.indexOfChild(attempt) + 1

                val nextParent =
                    guessWordsLayout.getChildAt(indexOfNextLine) as LinearLayout
                val nextActiveLetter = nextParent.getChildAt(0)
                nextActiveLetter.requestFocus()
            }
        }

        /** END OF GAME */
        else {
            var comparison = true
            for (i in 0 until attempt.childCount) {
                val compare = attempt.getChildAt(i) as TextView
                if (compare.text.toString()
                        .lowercase() != goalWord[i].toString()
                )
                    comparison = false
            }
            if (!comparison) {

                val messageText = "Lástima, fallaste!\nLa palabra era ${goalWord.uppercase()}"
                showEndGameDialog(context, goalWord, game, messageText)

                //STATS FILE UPDATE
                val statsFile = File(context.filesDir, "statsFile.json")
                if (statsFile.exists()) {
                    val stats = Stats().readStatsFile(statsFile)
                    stats.averageTries =
                        (((stats.timesPlayed * stats.averageTries!!) + (guessWordsLayout.indexOfChild(
                            attempt
                        ) + 1)) / (stats.timesPlayed + 1))
                    stats.timesPlayed++
                    Stats().writeStatsFile(statsFile, stats)
                } else {
                    val data = UserStats()
                    data.timesPlayed = 1
                    data.averageTries =
                        (guessWordsLayout.indexOfChild(attempt) + 1).toFloat()
                    data.timesGivenUp = 0
                    data.timesWon = 0
                    Stats().writeStatsFile(statsFile, data)
                }
            }
        }
    }

    private fun applyColorToLetter(comparedLetter: TextView, color: Int, keyboardLayout: LinearLayout) {
        comparedLetter.setBackgroundColor(color)

        for (row in 0 until keyboardLayout.childCount) {
            val keyboardRow =
                keyboardLayout.getChildAt(row) as LinearLayout

            for (button in 0 until keyboardRow.childCount) {
                val keyboardButton =
                    keyboardRow.getChildAt(button) as Button
                if (keyboardButton.text.toString() == comparedLetter.text.toString())
                    keyboardButton.setBackgroundColor(color)
            }
        }
    }

    private fun showEndGameDialog(context: Context, goalWord: String, game: Activity, messageText: String){
        MaterialAlertDialogBuilder(context)
            .setMessage(messageText)

            .setPositiveButton("Atrás") { _, _ ->
            }

            .setNegativeButton("Definición RAE de ${goalWord.uppercase()}") { _, _ ->
                // Respond to positive button press
                val website = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://dle.rae.es/${goalWord}")
                )
                context.startActivity(website)
            }

            .setNeutralButton("Nueva Partida") { _, _ ->
                // Respond to positive button press
                game.finish()
                val intent = Intent(game, game::class.java)
                game.startActivity(intent)
            }

            .show()
    }
}