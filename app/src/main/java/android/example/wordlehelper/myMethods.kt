package android.example.wordlehelper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException


class myMethods {


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

            if (activeLetterIndex == 4) {
                for (letterPosition in 0 until parent.childCount) {
                    val currentLetter = parent.getChildAt(letterPosition) as TextView
                    guessedWordLetters.add(currentLetter.text.toString().toLowerCase())
                }
                guessedWord = guessedWordLetters.joinToString("")


                //Comprobar que la palabra introducida es valida
                if (!wordList.contains(guessedWord)) {
                    val text = "La palabra no es válida"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(game, text, duration)
                    toast.setGravity(Gravity.CENTER, 0, 130)
                    toast.show()
                } else {
                    //LA PALABRA ES VALIDA, LET'S GO!!!

                    var alreadyGreen = mutableMapOf<Char, Boolean>()
                    guessedWord.forEach { letter ->
                        alreadyGreen.put(letter, false)
                    }

                    var listOfColors = mutableListOf<String>()

                    guessedWord.lowercase().forEachIndexed() { index, letter ->
                        /** LETRA VERDE*/
                        if (guessedWord[index] == goalWord[index]) {
                            listOfColors.add("GREEN")
                            alreadyGreen[letter] = true
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

                        /** LETRA AMARILLA*/
                        else if (goalWord.contains(letter) && !alreadyGreen[letter]!!) {
                            listOfColors.add("YELLOW")
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

                        /** LETRA NEGRA*/
                        else if (!goalWord.contains(letter) || alreadyGreen[letter]!!) {
                            listOfColors.add("BLACK")
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

                    /** SEGUNDO BARRIDO PARA CORREGIR COLORES*/
                    guessedWord.lowercase().forEachIndexed() { index, letter ->

                        if (alreadyGreen[letter]!! && listOfColors[index] == "YELLOW"){
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
                        /**COMPARACION DE PALABRA*/
                        var correcta: Boolean = true
                        for (i in 0 until parent.childCount) {
                            var compare = parent.getChildAt(i) as TextView
                            if (compare.text.toString()
                                    .lowercase() != goalWord[i].toString()
                            )
                                correcta = false
                        }

                        /** HAS ACERTADO*/
                        if (correcta == true) {

                            val statsFile = File(context.filesDir, "statsFile.json")
                            if (statsFile.exists()) {
                                var stats = Stats().readStatsFile(statsFile)
                                stats.averageTries =
                                    (((stats.timesPlayed * stats.averageTries!!) + (parentOfParent.indexOfChild(
                                        parent
                                    ) + 1)) / (stats.timesPlayed + 1))
                                stats.timesPlayed++
                                stats.timesWon++
                                Stats().writeStatsFile(statsFile, stats)
                            } else {
                                var data: UserStats = UserStats()
                                data.timesPlayed = 1
                                data.averageTries =
                                    (parentOfParent.indexOfChild(parent) + 1).toFloat()
                                data.timesGivenUp = 0
                                data.timesWon = 1
                                Stats().writeStatsFile(statsFile, data)
                            }

                            // DIALOG
                            MaterialAlertDialogBuilder(context)
                                .setMessage("Enhorabuena! Has acertado!")

                                .setPositiveButton("Atrás") { dialog, which ->
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

                            val statsFile = File(context.filesDir, "statsFile.json")
                            if (statsFile.exists()) {
                                var stats = Stats().readStatsFile(statsFile)
                                stats.averageTries =
                                    (((stats.timesPlayed * stats.averageTries!!) + (parentOfParent.indexOfChild(
                                        parent
                                    ) + 1)) / (stats.timesPlayed + 1))
                                stats.timesPlayed++
                                Stats().writeStatsFile(statsFile, stats)
                            } else {
                                var data: UserStats = UserStats()
                                data.timesPlayed = 1
                                data.averageTries =
                                    (parentOfParent.indexOfChild(parent) + 1).toFloat()
                                data.timesGivenUp = 0
                                data.timesWon = 0
                                Stats().writeStatsFile(statsFile, data)
                            }

                            //DIALOG
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

class UsedLetter(var letter: String, var isGreen: Boolean = false)


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
