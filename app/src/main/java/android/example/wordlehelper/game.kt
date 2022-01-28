package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import kotlin.random.Random

class game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var wordList: MutableList<String> = mutableListOf()
        wordList = myMethods().readWordsFromFile(this@game).toMutableList()
        val randomIndex = Random.nextInt(wordList.size);
        val goalWord = wordList[randomIndex]
        println(goalWord)


        /** INITIAL ACTIVE WORD TEXTVIEWS */
        var letraActiva = findViewById<View>(R.id.Guess11) as TextView
        letraActiva.requestFocus()
        val activeWord = findViewById<LinearLayout>(R.id.wordsContainerOverlay)
        for (row in 0 until activeWord.childCount) {
            val wordRow = activeWord.getChildAt(row) as LinearLayout
            for (casilla in 0 until wordRow.childCount) {
                val letter = wordRow.getChildAt(casilla) as TextView
                letter.addTextChangedListener(object : TextWatcher {
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
                        if (letter.text != "") {
                            val parent = letter.parent as LinearLayout
                            val letraActivaIndex = parent.indexOfChild(letter)
                            val nextLetraActiva = parent.getChildAt(letraActivaIndex + 1)
                            if (letraActivaIndex != parent.childCount - 1) nextLetraActiva.requestFocus()
                        }
                    }
                })
            }
        }

        /** KEYBOARD BUTTONS FUNCTIONS */
        val keyboardLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)

        for (row in 0 until keyboardLayout.childCount) {
            val keyboardRow = keyboardLayout.getChildAt(row) as LinearLayout

            for (button in 0 until keyboardRow.childCount) {
                val keyboardButton = keyboardRow.getChildAt(button) as Button
                keyboardButton.setOnClickListener {

                    //TECLAS DE LETRA
                    if (keyboardButton.text != "ENTER" && keyboardButton.text != "DEL") {
                        val activeLetter = currentFocus as TextView
                        activeLetter.text = keyboardButton.text
                    }

                    //TECLA BORRAR
                    else if (keyboardButton.text == "DEL") {
                        val activeLetter = currentFocus as TextView
                        val parent = activeLetter.parent as LinearLayout
                        val activeLetterIndex = parent.indexOfChild(activeLetter)

                        if (activeLetterIndex != 0) {
                            val nextLetraActiva =
                                parent.getChildAt(activeLetterIndex - 1) as TextView

                            if (activeLetterIndex != 4) {
                                nextLetraActiva.text = ""
                                nextLetraActiva.requestFocus()
                            } else {
                                if (activeLetter.text != "") {
                                    activeLetter.text = ""
                                }
                                else {
                                    nextLetraActiva.text = ""
                                    nextLetraActiva.requestFocus()
                                }
                            }
                        }
                    }

                    //TECLA ENTER
                    else if (keyboardButton.text.toString() == "ENTER") {
                        /**val activeWord = findViewById<LinearLayout>(R.id.wordsContainerOverlay)
                        for (row in 0 until activeWord.childCount) {
                            val wordRow = activeWord.getChildAt(row) as LinearLayout
                            for (casilla in 0 until wordRow.childCount) {
                                val letter = wordRow.getChildAt(casilla) as TextView*/

                    }


                }

            }
        }


    }
}