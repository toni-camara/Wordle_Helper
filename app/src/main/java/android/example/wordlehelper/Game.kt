package android.example.wordlehelper

import android.app.GameManager
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random


class Game : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /** LOAD LIST FROM FILE*/
        val wordList = MyMethods().readWordsFromFile(this) as MutableList<String>

        /** SELECT A RANDOM WORD FROM THE LIST AS OBJECTIVE OF THE GAME*/
        val randomIndex = Random.nextInt(wordList.size)
        val goalWord = wordList[randomIndex]

        /**GIVE UP BUTTON BEHAVIOR*/
        val giveUpButton = findViewById<View>(R.id.giveUpBtn)
        giveUpButton.setOnClickListener { showGiveUpDialogConfirmation(goalWord) }

        /** SET INITIAL FOCUS ON FIRST TEXTVIEW*/
        val activeLetterTextView = findViewById<View>(R.id.Guess11) as TextView
        activeLetterTextView.requestFocus()

        /** ADVANCE LETTER ON INPUT*/
        initNextFocusListeners()

        /** KEYBOARD BUTTONS LISTENERS */
        keyboardButtonsListeners(wordList, goalWord)
    }


    /** Shows a pop up dialog when GIVE UP button is pressed*/
    private fun showGiveUpDialogConfirmation(goalWord: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage("Seguro que quieres abandonar?")
            .setNegativeButton("Cancelar") { _, _ -> }
            .setPositiveButton("Abandonar") { _, _ ->
                updateStats()
                showGiveUpDialog(goalWord)
            }
            .show()
    }

    private fun updateStats() {
        val statsManager = StatsManager(this)
        val userStats = statsManager.getStats() ?: UserStats()
        userStats.timesPlayed++
        userStats.timesGivenUp++
        statsManager.storeStats(userStats)
    }

    private fun showGiveUpDialog(goalWord: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage("La palabra era ${goalWord.uppercase()}")
            .setNegativeButton("Salir a Menu Principal") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .setPositiveButton("Jugar otra vez") { _, _ ->
                val intent = Intent(this, Game::class.java)
                startActivity(intent)
                finish()
            }
            .show()
    }

    /** When a letter is typed, moves the current focus to the next TextView in the same row*/
    private fun initNextFocusListeners() {
        val guessWordsContainer = findViewById<LinearLayout>(R.id.wordsContainerOverlay)
        guessWordsContainer.forEach { guessWordRow ->
            (guessWordRow as LinearLayout).forEachIndexed { index, letterTextView ->
                (letterTextView as TextView).addTextChangedListener(object : TextWatcherAdapter() {
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (letterTextView.text.isNotEmpty()) {
                            val nextLetraActiva = guessWordRow.getChildAt(index + 1)
                            nextLetraActiva?.requestFocus()
                        }
                    }
                })
            }
        }
    }

    /** Sets up the different listeners for all buttons in the keyboard layout*/
    private fun keyboardButtonsListeners(wordList: MutableList<String>, goalWord: String) {
        val keyboardLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)

        for (row in 0 until keyboardLayout.childCount) {
            val keyboardRow = keyboardLayout.getChildAt(row) as LinearLayout

            for (button in 0 until keyboardRow.childCount) {
                val keyboardButton = keyboardRow.getChildAt(button) as Button

                when (keyboardButton.text) {
                    "ENTER" -> {
                        keyboardButton.setOnClickListener {
                            MyMethods().vibratePhone(this)
                            MyMethods().onEnterPress(
                                currentFocus as TextView, keyboardButton, wordList, goalWord, resources, keyboardLayout, this, this
                            )
                        }
                    }
                    "DEL" -> {
                        keyboardButton.setOnClickListener {
                            MyMethods().vibratePhone(this)
                            MyMethods().deletePress(currentFocus as TextView, keyboardButton)
                        }
                    }
                    else -> {
                        keyboardButton.setOnClickListener {
                            MyMethods().vibratePhone(this)
                            MyMethods().letterPress(currentFocus as TextView, keyboardButton)
                        }
                    }
                }
            }
        }
    }

    private fun onEnterKeyPressed() {

    }
}

private open class TextWatcherAdapter : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}

