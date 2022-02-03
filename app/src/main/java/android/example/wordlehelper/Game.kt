package android.example.wordlehelper

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class Game : AppCompatActivity() {

    /**SET UP DATABASE*/
    val database =
        Firebase.database("https://unlimitedwords-654c8-default-rtdb.europe-west1.firebasedatabase.app/").reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)



/*
/** UPLOAD TXT TO ONLINE DATABASE*/
val wordList = myMethods().readWordsFromFile(this@Game).toMutableList()
        wordList.forEach() { number ->
            println(number)
            val timesReviewed = 0
            val rating = 5.1f
            database.child("wordList").child(number.toString()).child("timesReviewed").setValue(timesReviewed)
            database.child("wordList").child(number.toString()).child("rating").setValue(rating)
        }
*/

        /** LOAD LIST FROM DATABASE*/

        var wordList = mutableListOf<String>()
        var randomIndex: Int = 0
        var goalWord: String = ""

        database.child("wordList").get().addOnSuccessListener {
            var word: String = ""
            it.children.forEach() { currentWord ->
                word = currentWord.key as String
                wordList.add(word)
                randomIndex = Random.nextInt(wordList.size)
                goalWord = wordList[randomIndex]
            }
        }


        /**HINT BUTTON*/
        val hintButton = findViewById<View>(R.id.hintBtn)
        hintButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                // Add customization options here
                .setMessage("La palabra era ${goalWord.uppercase()}")

                .setPositiveButton("Borrar Palabra") { dialog, which ->
                    // Respond to negative button press
                    database.child("wordList").child(goalWord).removeValue()
                }
                .setNegativeButton("Atras") { dialog, which ->
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


