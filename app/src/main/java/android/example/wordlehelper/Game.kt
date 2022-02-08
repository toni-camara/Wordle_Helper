package android.example.wordlehelper

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
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
import java.io.File
import kotlin.random.Random


class Game : AppCompatActivity() {

    /**SET UP DATABASE*/
    val database =
        Firebase.database("https://unlimitedwords-654c8-default-rtdb.europe-west1.firebasedatabase.app/").reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        /** LOAD LIST FROM DATABASE*/
/*
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
*/
        /** LOAD LIST FROM FILE*/
        val wordList = myMethods().readWordsFromFile(this) as MutableList<String>
        val randomIndex = Random.nextInt(wordList.size)
        val goalWord = wordList[randomIndex]
        println("La palabra objetivo es: $goalWord")

        /**GIVE UP BUTTON*/
        val giveUpButton = findViewById<View>(R.id.giveUpBtn)
        giveUpButton.setOnClickListener {


            //DIALOG
            MaterialAlertDialogBuilder(this)
                .setMessage("Seguro que quieres abandonar?")

                .setNegativeButton("Cancelar") { dialog, which ->
                }

                .setPositiveButton("Abandonar") { dialog, which ->
                    val statsFile = File(this.filesDir,"statsFile.json")
                    if(statsFile.exists()) {
                        var stats = Stats().readStatsFile(statsFile)
                        stats.timesPlayed++
                        stats.timesGivenUp ++
                        Stats().writeStatsFile(statsFile, stats)
                    }
                    else {
                        var data: UserStats = UserStats()
                        data.timesPlayed = 1
                        data.timesGivenUp = 1
                        data.timesWon = 0
                        Stats().writeStatsFile(statsFile, data)
                    }

                    MaterialAlertDialogBuilder(this)
                        .setMessage("La palabra era ${goalWord.uppercase()}")

                        .setNegativeButton("Salir a Menu Principal") { dialog, which ->
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        .setPositiveButton("Jugar otra vez") { dialog, which ->
                            finish();
                            val intent = Intent(this, Game::class.java)
                            startActivity(intent)
                        }
                        .show()
                }
                .show()
        }

        /** SET INITIAL FOCUS ON FIRST TEXTVIEW*/
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

        /** KEYBOARD BUTTONS LISTENERS */
        val keyboardLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)

        for (row in 0 until keyboardLayout.childCount) {
            val keyboardRow = keyboardLayout.getChildAt(row) as LinearLayout

            for (button in 0 until keyboardRow.childCount) {
                val keyboardButton = keyboardRow.getChildAt(button) as Button

                keyboardButton.setOnClickListener {

                    //VIBRACION DE TECLA
                    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        v.vibrate(
                            VibrationEffect.createOneShot(
                                50,
                                VibrationEffect.DEFAULT_AMPLITUDE
                            )
                        )

                    //LETTER KEYS
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


