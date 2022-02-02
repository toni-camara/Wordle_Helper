package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val wordList = myMethods().readWordsFromFile(this@Game).toMutableList()
        val randomIndex = Random.nextInt(wordList.size)
        val goalWord = wordList[randomIndex]


        /**HINT BUTTON*/
        val hintButton = findViewById<View>(R.id.hintBtn)
        hintButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                // Add customization options here
                .setMessage("La palabra era ${goalWord.uppercase()}")


                .setNegativeButton("atras") { dialog, which ->
                    // Respond to negative button press
                }
                .show()
        }

        /** INITIAL ACTIVE WORD TEXTVIEWS */
        val letraActiva = findViewById<View>(R.id.Guess11) as TextView
        letraActiva.requestFocus()

        /** ADVANCE LETTER ON INPUT*/
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
                    myMethods().letterPress(currentFocus as TextView, keyboardButton)

                    //TECLA BORRAR

                    myMethods().deletePress(currentFocus as TextView, keyboardButton)

                    //TECLA ENTER
                    myMethods().enterPress(
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


