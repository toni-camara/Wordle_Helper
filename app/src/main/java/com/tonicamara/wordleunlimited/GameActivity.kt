@file:Suppress("DEPRECATION")

package com.tonicamara.wordleunlimited

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.IOException
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private val myMethods = MyMethods()
    private lateinit var statsManager: StatsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        statsManager = StatsManager(this)


        /** LOAD LIST FROM FILE*/
        val wordList = readWordsFromFile(this) as MutableList<String>

        val goalWord = selectRandomWord(wordList)
        println("La palabra elegida es $goalWord") // Test purposes

        /**GIVE UP BUTTON BEHAVIOR*/
        val giveUpButton = findViewById<View>(R.id.giveUpBtn)
        giveUpButton.setOnClickListener {
            myMethods.vibratePhone(this)
            giveUpConfirmDialog(goalWord, this, this, statsManager)
        }

        /** SET INITIAL FOCUS ON FIRST TEXTVIEW*/
        val activeLetterTextView = findViewById<View>(R.id.Guess11) as TextView
        activeLetterTextView.requestFocus()

        /** ADVANCE LETTER ON INPUT*/
        initNextFocusListeners()

        /** KEYBOARD BUTTONS LISTENERS */
        keyboardButtonsListeners(wordList, goalWord)
    }

    private fun selectRandomWord(wordList: MutableList<String>): String{
        val randomIndex = Random.nextInt(wordList.size)
        return wordList[randomIndex]
    }

    private fun readWordsFromFile(context: Context): List<String> {
        var string = ""
        try {
            val inputStream = context.assets.open("wordList.txt")
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


    /** When a letter is typed, moves the current focus to the next TextView in the same row*/
    private fun initNextFocusListeners() {
        val guessWordsContainer = findViewById<LinearLayout>(R.id.wordsContainerOverlay)
        guessWordsContainer.forEach { guessWordRow ->
            (guessWordRow as LinearLayout).forEachIndexed { index, letterTextView ->
                (letterTextView as TextView).addTextChangedListener(object : TextWatcherAdapter() {
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (letterTextView.text.isNotEmpty()) {
                            val nextActiveLetter = guessWordRow.getChildAt(index + 1)
                            nextActiveLetter?.requestFocus()
                        }
                    }
                })
            }
        }
    }

    private fun keyboardButtonsListeners(wordList: MutableList<String>, goalWord: String) {
        val keyboardLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)
        keyboardLayout.forEach { keyboardRow ->
            (keyboardRow as LinearLayout).forEach { keyboardButton ->
                keyboardButton as Button
                when (keyboardButton.text) {
                    "ENTER" -> {
                        keyboardButton.setOnClickListener { onEnterPress(keyboardButton, wordList, goalWord) }
                    }
                    "DEL" -> {
                        keyboardButton.setOnClickListener { onDeletePress(currentFocus as TextView, keyboardButton) }
                    }
                    else -> {
                        keyboardButton.setOnClickListener { onLetterPress(currentFocus as TextView, keyboardButton) }
                    }
                }
            }
        }
    }

    private fun onLetterPress(currentFocus: TextView, keyboardButton: TextView) {
        myMethods.vibratePhone(this)

        if (keyboardButton.text != "ENTER" && keyboardButton.text != "DEL") {
            if (currentFocus.text.isEmpty()) {
                currentFocus.text = keyboardButton.text
            }
        }
    }

    private fun onDeletePress(currentFocus: TextView, keyboardButton: TextView) {
        myMethods.vibratePhone(this)

        if (keyboardButton.text == "DEL") {
            val parent = currentFocus.parent as LinearLayout
            val activeLetterIndex = parent.indexOfChild(currentFocus)
            if (activeLetterIndex != 0) {
                val nextActiveLetter =
                    parent.getChildAt(activeLetterIndex - 1) as TextView
                if (activeLetterIndex != LAST_LETTER_INDEX) {
                    nextActiveLetter.text = ""
                    nextActiveLetter.requestFocus()
                } else {
                    if (currentFocus.text.toString().isNotEmpty()) {
                        currentFocus.text = ""
                    } else {
                        nextActiveLetter.text = ""
                        nextActiveLetter.requestFocus()
                    }
                }
            }
        }
    }

    private fun onEnterPress(keyboardButton: TextView, wordList: MutableList<String>, goalWord: String) {
        myMethods.vibratePhone(this)

        if (keyboardButton.text.toString() == "ENTER") {
            val activeLetterIndex = (currentFocus?.parent as LinearLayout).indexOfChild(currentFocus)
            if (activeLetterIndex == LAST_LETTER_INDEX) {
                val guessedWord = getGuessedLetters(this)
                if (!wordList.contains(guessedWord)) {
                    showInvalidWordToast()
                } else {
                    onValidWord(guessedWord, goalWord)
                }
            }
        }
    }

    private fun onValidWord(guessedWord: String, goalWord: String) {
        letterColorChange(guessedWord, goalWord)
        val attempt = currentFocus?.parent as LinearLayout
        val guessWordsLayout = attempt.parent as LinearLayout
        if (guessWordsLayout.indexOfChild(attempt) < 5) {
            if (haveYouWon(attempt, goalWord)) {
                val messageText = "Enhorabuena! Has acertado!"
                showEndGameDialog(goalWord, messageText)
                gameOver(attempt, goalWord, guessWordsLayout)
            } else moveToNextGuess(guessWordsLayout, attempt)
        } else {
            gameOver(attempt, goalWord, guessWordsLayout)
        }
    }

    private fun gameOver(attempt: LinearLayout, goalWord: String, guessWordsLayout: LinearLayout) {
        var victory = false
        val tries = guessWordsLayout.indexOfChild(attempt) + 1
        if (!haveYouWon(attempt, goalWord)) {
            val messageText = "Lástima, fallaste!\nLa palabra era ${goalWord.uppercase()}"
            showEndGameDialog(goalWord, messageText)
            statsManager.updateStatsGameFinished(tries, victory)
        } else {
            val messageText = "Enhorabuena! Has acertado!"
            showEndGameDialog(goalWord, messageText)
            victory = true
            statsManager.updateStatsGameFinished(tries, victory)
        }
    }

    private fun moveToNextGuess(guessWordsLayout: LinearLayout, attempt: LinearLayout) {
        val indexOfNextLine = guessWordsLayout.indexOfChild(attempt) + 1
        val nextParent = guessWordsLayout.getChildAt(indexOfNextLine) as LinearLayout
        val nextActiveLetter = nextParent.getChildAt(0)
        nextActiveLetter.requestFocus()
    }

    private fun haveYouWon(attempt: LinearLayout, goalWord: String): Boolean {
        attempt.forEachIndexed { index, comparedLetter ->
            if ((comparedLetter as TextView).text.toString().lowercase() != goalWord[index].toString())
                return false
        }
        return true
    }

    private fun showInvalidWordToast() {
        val text = "La palabra no es válida"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(this, text, duration)
        toast.setGravity(Gravity.CENTER, 0, 130)
        toast.show()
    }

    private fun getGuessedLetters(game: Activity): String {
        val guessedWordLetters = mutableListOf<String>()
        val parent = game.currentFocus?.parent as LinearLayout
        val activeLetterIndex = parent.indexOfChild(game.currentFocus)
        if (activeLetterIndex == LAST_LETTER_INDEX) {
            parent.forEach { currentLetter ->
                guessedWordLetters.add((currentLetter as TextView).text.toString().lowercase())
            }
        }
        return guessedWordLetters.joinToString("")
    }

    private fun letterColorChange(guessedWord: String, goalWord: String) {
        val alreadyGreen = mutableMapOf<Char, Boolean>()
        guessedWord.forEach { letter ->
            alreadyGreen[letter] = false
        }
        val letterColors = mutableListOf<Int>()
        firstPassColoring(guessedWord, goalWord, letterColors, alreadyGreen)
        secondPassColoring(guessedWord, alreadyGreen, letterColors)
    }

    private fun firstPassColoring(
        guessedWord: String,
        goalWord: String,
        letterColors: MutableList<Int>,
        alreadyGreen: MutableMap<Char, Boolean>
    ) {
        val colorStates = ColorStates(resources)
        guessedWord.lowercase().forEachIndexed { index, letter ->
            val comparedLetter = (currentFocus?.parent as LinearLayout).getChildAt(index) as TextView

            if (guessedWord[index] == goalWord[index]) {
                letterColors.add(colorStates.correctLetterColor)
                alreadyGreen[letter] = true
                applyColorToLetter(comparedLetter, colorStates.correctLetterColor)
            } else if (goalWord.contains(letter) && !alreadyGreen[letter]!!) {
                letterColors.add(colorStates.misplacedLetterColor)
                applyColorToLetter(comparedLetter, colorStates.misplacedLetterColor)
            } else if (!goalWord.contains(letter) || alreadyGreen[letter]!!) {
                letterColors.add(colorStates.wrongLetterColor)
                applyColorToLetter(comparedLetter, colorStates.wrongLetterColor)
            }
        }
    }

    private fun secondPassColoring(guessedWord: String, alreadyGreen: MutableMap<Char, Boolean>, letterColors: MutableList<Int>) {
        val colorStates = ColorStates(resources)
        guessedWord.lowercase().forEachIndexed { index, letter ->
            if (alreadyGreen[letter]!! && letterColors[index] == colorStates.misplacedLetterColor) {
                val comparedLetter = (currentFocus?.parent as LinearLayout).getChildAt(index) as TextView
                comparedLetter.setBackgroundColor(colorStates.wrongLetterColor)
            }
        }
    }

    private fun applyColorToLetter(comparedLetter: TextView, color: Int) {
        comparedLetter.setBackgroundColor(color)
        val keyboardLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)
        keyboardLayout.forEach { keyboardRow ->
            (keyboardRow as LinearLayout).forEach { keyboardButton ->
                keyboardButton as Button
                if (keyboardButton.text.toString() == comparedLetter.text.toString())
                    keyboardButton.setBackgroundColor(color)
            }
        }
    }

    private fun showEndGameDialog(goalWord: String, messageText: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(messageText)
            .setPositiveButton("Salir al Menu Principal") { _, _ ->
                myMethods.vibratePhone(this)
                this.finish()
            }
            .setNegativeButton("Definición RAE de ${goalWord.uppercase()}") { _, _ ->
                // Respond to positive button press
                myMethods.vibratePhone(this)
                val website = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://dle.rae.es/${goalWord}")
                )
                this.startActivity(website)
            }
            .setNeutralButton("Nueva Partida") { _, _ ->
                // Respond to positive button press
                myMethods.vibratePhone(this)
                val intent = Intent(this, GameActivity::class.java)
                this.startActivity(intent)
                this.finish()
            }
            .setCancelable(false)
            .show()

    }

    private fun giveUpConfirmDialog(goalWord: String, context: Context, gameActivity: GameActivity, statsManager: StatsManager) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Seguro que quieres abandonar?")
            .setNegativeButton("Cancelar") { _, _ ->
                myMethods.vibratePhone(this)
            }
            .setPositiveButton("Abandonar") { _, _ ->
                myMethods.vibratePhone(this)
                statsManager.updateStatsGiveUp()
                showGiveUpDialog(goalWord, context, gameActivity)
            }
            .setCancelable(false)
            .show()
    }

    private fun showGiveUpDialog(goalWord: String, context: Context, gameActivity: GameActivity) {
        MaterialAlertDialogBuilder(context)
            .setMessage("La palabra era ${goalWord.uppercase()}")
            .setNeutralButton("Salir al Menu Principal") { _, _ ->
                myMethods.vibratePhone(this)
                gameActivity.finish()
            }
            .setNegativeButton("Definición RAE de ${goalWord.uppercase()}") { _, _ ->
                // Respond to positive button press
                myMethods.vibratePhone(this)
                val website = Intent(Intent.ACTION_VIEW, Uri.parse("https://dle.rae.es/${goalWord}"))
                this.startActivity(website)
            }
            .setPositiveButton("Jugar otra vez") { _, _ ->
                myMethods.vibratePhone(this)
                val intent = Intent(context, GameActivity::class.java)
                gameActivity.startActivity(intent)
                gameActivity.finish()
            }
            .setCancelable(false)
            .show()
    }

}

private const val LAST_LETTER_INDEX = 4

private class ColorStates(resources: Resources) {
    val correctLetterColor = resources.getColor(R.color.green_letter)
    val misplacedLetterColor = resources.getColor(R.color.yellow_letter)
    val wrongLetterColor = resources.getColor(R.color.black_letter)
}

private open class TextWatcherAdapter : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}

