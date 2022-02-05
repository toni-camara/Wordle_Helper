package android.example.wordlehelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.IOException


class myMethods {

    /** This method draws the list of words */
    fun drawWordList(filteredList: List<String>, Activity: Activity) {
        Activity.findViewById<LinearLayout>(R.id.verticalWordsLeft).removeAllViews()
        Activity.findViewById<LinearLayout>(R.id.verticalWordsCenter).removeAllViews()
        Activity.findViewById<LinearLayout>(R.id.verticalWordsRight).removeAllViews()

        filteredList.forEachIndexed() { index, word ->
            // Word list settings
            val vistaNueva = TextView(Activity)
            vistaNueva.textSize = 20f
            vistaNueva.text = word.uppercase()
            vistaNueva.setTextColor(Color.parseColor("#6746c3"))
            val typeface =
                Typeface.SANS_SERIF//createFromAsset(applicationContext.assets, "sans-serif-light")
            vistaNueva.setTypeface(typeface)

            //Word list printing
            if ((index + 1).mod(3) < 0.4) {
                Activity.findViewById<LinearLayout>(R.id.verticalWordsRight).addView(vistaNueva)
            } else if ((index + 1).mod(3) > 0.4 && index.mod(3) < 1) {
                Activity.findViewById<LinearLayout>(R.id.verticalWordsLeft).addView(vistaNueva)
            } else Activity.findViewById<LinearLayout>(R.id.verticalWordsCenter).addView(vistaNueva)

        }
    }

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

    /** This method takes a Character input and searches the list for words containing that input */
    fun searchForInput(wordList: MutableList<String>, input: List<input>): List<String> {
        var updatedList = wordList.toMutableList()

        for (word in wordList) {
            word.lowercase().forEachIndexed() { index, letter ->
                input.forEachIndexed() { inputIndex, inputLetter ->

                    // Si la letra que toca corresponde a una verde en el input
                    val comparacion = input[inputIndex].color
                    // MAPAS: input.get busca el color (value) de LETTER (key) en mi mapa de input
                    // Si no existe LETTER en mi input, devuelve null
                    if (comparacion == android.example.wordlehelper.input.letterColor.GREEN && index == inputIndex && input[inputIndex].letter == letter) {
                        updatedList = deleteRestOfWordsGreen(updatedList, letter, index)
                    }
                    // Si la palabra contiene amarillas
                    else if (comparacion == android.example.wordlehelper.input.letterColor.YELLOW && input[inputIndex].letter == letter) {
                        updatedList = deleteRestOfWordsYellow(updatedList, letter, index)
                    }
                    // Si la palabra contiene negras
                    else if (comparacion == android.example.wordlehelper.input.letterColor.BLACK && input[inputIndex].letter == letter)
                        updatedList.remove(word)
                    //   }
                }


            }
        }
        wordList.forEach { println(it) }
        return updatedList
    }

    /** This method removes from the list every word that doesn't fit the Green user inputs*/
    fun deleteRestOfWordsGreen(
        wordList: MutableList<String>,
        inputLetter: Char,
        posicion: Int
    ): MutableList<String> {
        val updatedList = wordList.toMutableList()
        for (word in wordList) {
            if (!word.contains(inputLetter.toString())) {
                updatedList.remove(word)
                println(word)
            } else {
                word.lowercase().forEachIndexed() { index, letter ->
                    if (letter != inputLetter && posicion == index)
                        updatedList.remove(word)
                }
            }
        }
        return updatedList.sorted().toMutableList()
    }

    /** This method removes from the list every word that doesn't fit the Yellow user inputs*/
    fun deleteRestOfWordsYellow(
        wordList: MutableList<String>,
        inputLetter: Char,
        posicion: Int
    ): MutableList<String> {
        val updatedList = wordList.toMutableList()
        for (word in wordList) {
            if (!word.contains(inputLetter.toString())) updatedList.remove(word)

            /**var letterIsInWord = false
            word.lowercase().forEachIndexed() { index, letter ->
            if (letter == inputLetter) letterIsInWord = true
            }
            if (!letterIsInWord) {
            updatedList.remove(word)
            letterIsInWord = false
            }*/
        }
        return updatedList.sorted().toMutableList()
    }


    fun letterPress(currentFocus: TextView, keyboardButton: TextView) {
        val activeLetter = currentFocus as TextView
        val parent = activeLetter.parent as LinearLayout
        val activeLetterIndex = parent.indexOfChild(activeLetter)


        if (keyboardButton.text != "ENTER" && keyboardButton.text != "DEL") {
            if (activeLetter.text.toString() == "") {
                val activeLetter = currentFocus as TextView
                activeLetter.text = keyboardButton.text
            }
        }
    }


    fun deletePress(currentFocus: TextView, keyboardButton: TextView) {

        if (keyboardButton.text == "DEL") {
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
                    if (activeLetter.text.toString() != "") {
                        activeLetter.text = ""
                    } else {
                        nextLetraActiva.text = ""
                        nextLetraActiva.requestFocus()
                    }
                }
            }
        }
    }


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
            val activeLetter = currentFocus as TextView
            val parent = activeLetter.parent as LinearLayout
            val activeLetterIndex = parent.indexOfChild(activeLetter)

            //Recoger palabra de las letras en la fila
            var guessedWord: String
            val guessedWordLetters = mutableListOf<String>()

            val timesUsed = mutableListOf<UsedLetter>()

            if (activeLetterIndex == 4) {
                for (letterPosition in 0 until parent.childCount) {
                    val currentLetter = parent.getChildAt(letterPosition) as TextView
                    guessedWordLetters.add(currentLetter.text.toString().toLowerCase())
                   //TODO get number of times letter is used
                    val buffer = UsedLetter(currentLetter.text.toString(), 0)
                    for (index in 0 until timesUsed.size) {
                            timesUsed.add(buffer)
                            timesUsed[letterPosition].timesUsed++
                    }
                }
                guessedWord = guessedWordLetters.joinToString("")

                for (index in 0 until timesUsed.size) {
                    println("La letra ${timesUsed[index].letter} se ha usado ${timesUsed[index].timesUsed} veces")
                }


                //Comprobar que la palabra introducida es valida
                if (!wordList.contains(guessedWord)) {
                    //TODO Word invalid
                } else {
                    //LA PALABRA ES VALIDA, LET'S GO!!!
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
                            MaterialAlertDialogBuilder(context)
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
                                    context.startActivity(website)
                                }
                                .setNeutralButton("Nueva Partida") { dialog, which ->
                                    // Respond to positive button press
                                    game.finish();
                                    val intent = Intent(game, game::class.java)
                                    game.startActivity(intent);
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
                            MaterialAlertDialogBuilder(context)
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
                                    context.startActivity(website)
                                }
                                .setNeutralButton("Nueva Partida") { dialog, which ->
                                    // Respond to positive button press
                                    game.finish();
                                    val intent = Intent(game, game::class.java)
                                    game.startActivity(intent);
                                }
                                .show()
                        }
                    }
                }
            }
        }
    }

}


class input(val letter: Char?, val color: letterColor) {
    enum class letterColor {
        GREEN, YELLOW, BLACK, GREY
    }
}

class UsedLetter(var letter: String, var timesUsed: Int = 0)


/** UPLOAD TXT TO ONLINE DATABASE*/
/*
val goalWord: String = "goalw"
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
