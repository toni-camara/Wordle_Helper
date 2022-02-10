@file:Suppress("DEPRECATION")

package android.example.wordlehelper

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


@Suppress("DEPRECATION")
class Game : AppCompatActivity() {
    private val myMethods = MyMethods()
    private lateinit var statsManager: StatsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        statsManager = StatsManager(this)


        /** LOAD LIST FROM FILE*/
        val wordList = readWordsFromFile(this) as MutableList<String>

        /** SELECT A RANDOM WORD FROM THE LIST AS OBJECTIVE OF THE GAME*/
        val randomIndex = Random.nextInt(wordList.size)
        val goalWord = wordList[randomIndex]
        println("La palabra es $goalWord")

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
        keyboardButtonsListeners(wordList, goalWord, statsManager)
    }

    private fun readWordsFromFile(context: Context): List<String> {
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

    private fun keyboardButtonsListeners(wordList: MutableList<String>, goalWord: String, statsManager: StatsManager) {
        val keyboardLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)
        keyboardLayout.forEach { keyboardRow ->
            (keyboardRow as LinearLayout).forEach { keyboardButton ->
                keyboardButton as Button
                when (keyboardButton.text) {
                    "ENTER" -> {
                        keyboardButton.setOnClickListener { onEnterPress(keyboardButton, wordList, goalWord, statsManager) }
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
            if (currentFocus.text.toString() == "") {
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

    private fun onEnterPress(keyboardButton: TextView, wordList: MutableList<String>, goalWord: String, statsManager: StatsManager) {
        myMethods.vibratePhone(this)

        if (keyboardButton.text.toString() == "ENTER") {
            val activeLetterIndex = (currentFocus?.parent as LinearLayout).indexOfChild(currentFocus)
            if (activeLetterIndex == 4) {
                val guessedWord = getGuessedLetters(this)
                if (!wordList.contains(guessedWord)) {
                    showInvalidWordToast()
                } else {
                    onValidWord(guessedWord, goalWord, statsManager)
                }
            }
        }
    }

    private fun onValidWord(guessedWord: String, goalWord: String, statsManager: StatsManager) {
        letterColorChange(guessedWord, goalWord)
        val attempt = currentFocus?.parent as LinearLayout
        val guessWordsLayout = attempt.parent as LinearLayout
        if (guessWordsLayout.indexOfChild(attempt) < 5) {
            if (haveYouWon(attempt, goalWord)) {
                val messageText = "Enhorabuena! Has acertado!"
                showEndGameDialog(this, goalWord, this, messageText)
                statsManager.victoryStatsUpdate(this, attempt, guessWordsLayout)
            } else moveToNextGuess(guessWordsLayout, attempt)
        } else {
            gameOver(attempt, goalWord, guessWordsLayout, statsManager)
        }
    }

    private fun gameOver(attempt: LinearLayout, goalWord: String, guessWordsLayout: LinearLayout, statsManager: StatsManager) {
        if (!haveYouWon(attempt, goalWord)) {
            val messageText = "Lástima, fallaste!\nLa palabra era ${goalWord.uppercase()}"
            showEndGameDialog(this, goalWord, this, messageText)
            statsManager.defeatStatsUpdate(this, attempt, guessWordsLayout)
        } else {
            val messageText = "Enhorabuena! Has acertado!"
            showEndGameDialog(this, goalWord, this, messageText)
            statsManager.victoryStatsUpdate(this, attempt, guessWordsLayout)
        }
    }

    private fun moveToNextGuess(guessWordsLayout: LinearLayout, attempt: LinearLayout) {
        val indexOfNextLine = guessWordsLayout.indexOfChild(attempt) + 1
        val nextParent = guessWordsLayout.getChildAt(indexOfNextLine) as LinearLayout
        val nextActiveLetter = nextParent.getChildAt(0)
        nextActiveLetter.requestFocus()
    }

    private fun haveYouWon(attempt: LinearLayout, goalWord: String): Boolean {
        for (i in 0 until attempt.childCount) {
            val comparedLetter = attempt.getChildAt(i) as TextView
            if (comparedLetter.text.toString().lowercase() != goalWord[i].toString())
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
        if (activeLetterIndex == 4) {
            for (letterPosition in 0 until parent.childCount) {
                val currentLetter = parent.getChildAt(letterPosition) as TextView
                guessedWordLetters.add(currentLetter.text.toString().lowercase())
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
                letterColors.add(colorStates.green)
                alreadyGreen[letter] = true
                applyColorToLetter(comparedLetter, colorStates.green)
            } else if (goalWord.contains(letter) && !alreadyGreen[letter]!!) {
                letterColors.add(colorStates.yellow)
                applyColorToLetter(comparedLetter, colorStates.yellow)
            } else if (!goalWord.contains(letter) || alreadyGreen[letter]!!) {
                letterColors.add(colorStates.black)
                applyColorToLetter(comparedLetter, colorStates.black)
            }
        }
    }

    private fun secondPassColoring(guessedWord: String, alreadyGreen: MutableMap<Char, Boolean>, letterColors: MutableList<Int>) {
        val colorStates = ColorStates(resources)
        guessedWord.lowercase().forEachIndexed { index, letter ->
            if (alreadyGreen[letter]!! && letterColors[index] == colorStates.yellow) {
                val comparedLetter = (currentFocus?.parent as LinearLayout).getChildAt(index) as TextView
                comparedLetter.setBackgroundColor(colorStates.black)
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

    private fun showEndGameDialog(context: Context, goalWord: String, game: Activity, messageText: String) {
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

    private fun giveUpConfirmDialog(goalWord: String, context: Context, game: Game, statsManager: StatsManager) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Seguro que quieres abandonar?")
            .setNegativeButton("Cancelar") { _, _ -> }
            .setPositiveButton("Abandonar") { _, _ ->
                statsManager.onGiveUpUpdateStats()
                showGiveUpDialog(goalWord, context, game)
            }
            .show()
    }

    private fun showGiveUpDialog(goalWord: String, context: Context, game: Game) {
        MaterialAlertDialogBuilder(context)
            .setMessage("La palabra era ${goalWord.uppercase()}")
            .setNegativeButton("Salir a Menu Principal") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                game.startActivity(intent)
                game.finish()
            }
            .setPositiveButton("Jugar otra vez") { _, _ ->
                val intent = Intent(context, Game::class.java)
                game.startActivity(intent)
                game.finish()
            }
            .show()
    }

}

private class ColorStates(resources: Resources) {
    val green = resources.getColor(R.color.verde)
    val yellow = resources.getColor(R.color.amarillo)
    val black = resources.getColor(R.color.negro)
}

private open class TextWatcherAdapter : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}

