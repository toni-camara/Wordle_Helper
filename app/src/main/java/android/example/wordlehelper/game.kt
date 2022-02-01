package android.example.wordlehelper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.FocusFinder
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.random.Random

class game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var wordList: MutableList<String> = mutableListOf()
        wordList = myMethods().readWordsFromFile(this@game).toMutableList()
        val randomIndex = Random.nextInt(wordList.size);
        val goalWord = wordList[randomIndex]


        /**HINT BUTTON*/
        var hintButton = findViewById<View>(R.id.hintBtn)
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
        var letraActiva = findViewById<View>(R.id.Guess11) as TextView
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

//TODO
                    /**if (keyboardButton.text.toString() == "ENTER") {
                    val activeLetter = currentFocus as TextView
                    val parent = activeLetter.parent as LinearLayout
                    val activeLetterIndex = parent.indexOfChild(activeLetter)


                    val guessedWordLetters = mutableListOf<String>()

                    //Recoger palabra de las letras en la fila
                    var guessedWord: String
                    if (activeLetterIndex == 4) {
                    for (letter in 0 until parent.childCount) {
                    val currentLetter = parent.getChildAt(letter) as TextView
                    guessedWordLetters.add(currentLetter.text.toString().toLowerCase())
                    }
                    guessedWord = guessedWordLetters.joinToString("")

                    //Comprobar que la palabra introducida es valida
                    if (!wordList.contains(guessedWord)) println("la palabra $guessedWord no es válida")
                    else {
                    //LA PALABRA ES VALIDA, LET'S GO!!!
                    println("La palabra a adivinar es $goalWord \n Tu palabra introducida es $guessedWord y es valida")
                    guessedWord.lowercase().forEachIndexed() { index, letter ->

                    //VERDE
                    if (guessedWord[index] == goalWord[index]) {
                    val comparedLetter = parent.getChildAt(index) as TextView
                    comparedLetter.setBackgroundColor(resources.getColor(R.color.verde))

                    for (row in 0 until keyboardLayout.childCount) {
                    val keyboardRow =
                    keyboardLayout.getChildAt(row) as LinearLayout

                    for (button in 0 until keyboardRow.childCount) {
                    val keyboardButton =
                    keyboardRow.getChildAt(button) as Button
                    if (keyboardButton.text.toString() == comparedLetter.text.toString())
                    keyboardButton.setBackgroundColor(
                    resources.getColor(
                    R.color.verde
                    )
                    )
                    }
                    }

                    }

                    //AMARILLO
                    else if (goalWord.contains(letter)) {
                    val comparedLetter = parent.getChildAt(index) as TextView
                    comparedLetter.setBackgroundColor(resources.getColor(R.color.amarillo))

                    for (row in 0 until keyboardLayout.childCount) {
                    val keyboardRow =
                    keyboardLayout.getChildAt(row) as LinearLayout

                    for (button in 0 until keyboardRow.childCount) {
                    val keyboardButton =
                    keyboardRow.getChildAt(button) as Button
                    if (keyboardButton.text.toString() == comparedLetter.text.toString())
                    keyboardButton.setBackgroundColor(
                    resources.getColor(
                    R.color.amarillo
                    )
                    )
                    }
                    }
                    }

                    //NEGRO
                    else if (!goalWord.contains(letter)) {
                    val comparedLetter = parent.getChildAt(index) as TextView
                    comparedLetter.setBackgroundColor(resources.getColor(R.color.negro))

                    for (row in 0 until keyboardLayout.childCount) {
                    val keyboardRow =
                    keyboardLayout.getChildAt(row) as LinearLayout

                    for (button in 0 until keyboardRow.childCount) {
                    val keyboardButton =
                    keyboardRow.getChildAt(button) as Button
                    if (keyboardButton.text.toString() == comparedLetter.text.toString())
                    keyboardButton.setBackgroundColor(
                    resources.getColor(
                    R.color.negro
                    )
                    )
                    }
                    }
                    }

                    }


                    val activeLetter = currentFocus as TextView
                    val parent = activeLetter.parent as LinearLayout
                    val parentOfParent = parent.parent as LinearLayout


                    var indexOfNextLine = parentOfParent.indexOfChild(parent)
                    if (parentOfParent.indexOfChild(parent) < 5) {
                    /**HAS ACERTADO*/
                    var correcta: Boolean = true
                    for (i in 0 until parent.childCount) {
                    var compare = parent.getChildAt(i) as TextView
                    if (compare.text.toString()
                    .lowercase() != goalWord[i].toString()
                    )
                    correcta = false
                    }
                    if (correcta == true) {
                    MaterialAlertDialogBuilder(this)
                    // Add customization options here
                    .setMessage("Enhorabuena! Has acertado!")

                    .setPositiveButton("Atrás") { dialog, which ->
                    // Respond to negative button press
                    }
                    .setNegativeButton("Definición RAE de ${goalWord.uppercase()}") { dialog, which ->
                    // Respond to positive button press
                    val website = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://dle.rae.es/${goalWord.toString()}")
                    )
                    startActivity(website)
                    }
                    .setNeutralButton("Nueva Partida") { dialog, which ->
                    // Respond to positive button press
                    finish();
                    startActivity(getIntent());
                    }

                    .show()
                    }

                    /** NO HAS ACERTADO*/
                    else {
                    indexOfNextLine = parentOfParent.indexOfChild(parent) + 1

                    val nextParent =
                    parentOfParent.getChildAt(indexOfNextLine) as LinearLayout
                    val nextActiveLetter = nextParent.getChildAt(0)
                    nextActiveLetter.requestFocus()
                    }
                    }

                    /** FIN DEL JUEGO */
                    else {
                    var correcta: Boolean = true
                    for (i in 0 until parent.childCount) {
                    var compare = parent.getChildAt(i) as TextView
                    if (compare.text.toString()
                    .lowercase() != goalWord[i].toString()
                    )
                    correcta = false
                    }
                    if (correcta == false) {
                    MaterialAlertDialogBuilder(this)
                    // Add customization options here
                    .setMessage("Lástima, fallaste!\nLa palabra era ${goalWord.uppercase()}")

                    .setPositiveButton("Atrás") { dialog, which ->
                    // Respond to negative button press
                    }
                    .setNegativeButton("Definición RAE de ${goalWord.uppercase()}") { dialog, which ->
                    // Respond to positive button press
                    val website = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://dle.rae.es/${goalWord.toString()}")
                    )
                    startActivity(website)
                    }
                    .setNeutralButton("Nueva Partida") { dialog, which ->
                    // Respond to positive button press
                    finish();
                    startActivity(getIntent());
                    }
                    .show()
                    }
                    }
                    }
                    }
                    }
                    }
                     */ //TODO end of comment

                }
            }
        }
    }
}


