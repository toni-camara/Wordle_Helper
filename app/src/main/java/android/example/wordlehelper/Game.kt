package android.example.wordlehelper

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
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
        giveUpButton.setOnClickListener {
            giveupDialog(goalWord)
        }

        /** SET INITIAL FOCUS ON FIRST TEXTVIEW*/
        val activeLetterTextView = findViewById<View>(R.id.Guess11) as TextView
        activeLetterTextView.requestFocus()

        /** ADVANCE LETTER ON INPUT*/
        moveFocusOnInput()

        /** KEYBOARD BUTTONS LISTENERS */
        keyboardButtonsListeners(wordList, goalWord)
    }


    /** Shows a pop up dialog when GIVE UP button is pressed*/
    private fun giveupDialog(goalWord: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage("Seguro que quieres abandonar?")

            .setNegativeButton("Cancelar") { _, _ ->
            }

            .setPositiveButton("Abandonar") { _, _ ->
                val statsFile = File(this.filesDir, "statsFile.json")
                if (statsFile.exists()) {
                    val stats = Stats().readStatsFile(statsFile)
                    stats.timesPlayed++
                    stats.timesGivenUp++
                    Stats().writeStatsFile(statsFile, stats)
                } else {
                    val data = UserStats()
                    data.timesPlayed = 1
                    data.timesGivenUp = 1
                    data.timesWon = 0
                    Stats().writeStatsFile(statsFile, data)
                }

                MaterialAlertDialogBuilder(this)
                    .setMessage("La palabra era ${goalWord.uppercase()}")

                    .setNegativeButton("Salir a Menu Principal") { _, _ ->
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .setPositiveButton("Jugar otra vez") { _, _ ->
                        finish()
                        val intent = Intent(this, Game::class.java)
                        startActivity(intent)
                    }
                    .show()
            }
            .show()
    }

    /** When a letter is typed, moves the current focus to the next TextView in the same row*/
    private fun moveFocusOnInput() {

        val guessWordsContainer = findViewById<LinearLayout>(R.id.wordsContainerOverlay)
        for (row in 0 until guessWordsContainer.childCount) {

            val guessWordRow = guessWordsContainer.getChildAt(row) as LinearLayout
            for (letter in 0 until guessWordRow.childCount) {

                val letterTextView = guessWordRow.getChildAt(letter) as TextView
                letterTextView.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {}

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (letterTextView.text != "") {
                            val parent = letterTextView.parent as LinearLayout
                            val letraActivaIndex = parent.indexOfChild(letterTextView)
                            val nextLetraActiva = parent.getChildAt(letraActivaIndex + 1)
                            if (letraActivaIndex != parent.childCount - 1) nextLetraActiva.requestFocus()
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

                keyboardButton.setOnClickListener {

                    MyMethods().vibratePhone(this)

                    MyMethods().letterPress(currentFocus as TextView, keyboardButton)

                    MyMethods().deletePress(currentFocus as TextView, keyboardButton)

                    MyMethods().enterPress(
                        currentFocus as TextView,
                        keyboardButton,
                        wordList,
                        goalWord,
                        resources,
                        keyboardLayout,
                        this,
                        this
                    )
                }
            }
        }
    }
}


