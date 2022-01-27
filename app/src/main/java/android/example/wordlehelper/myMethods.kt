package android.example.wordlehelper

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import java.io.IOException


class myMethods {

    /** returns 'True' if the value is odd, and 'False' if the value is even*/
    fun isOdd(value: Int): Boolean {
        return value and 0x01 != 0
    }

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
                input.forEachIndexed(){inputIndex,inputLetter ->
                    //if (!word.contains(inputLetter.toString())) updatedList.remove(word)
                    //else {

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
            }
            else {
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
            if(!word.contains(inputLetter.toString())) updatedList.remove(word)

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

}

class input(val letter:Char?, val color: letterColor) {
    enum class letterColor {
        GREEN, YELLOW, BLACK, GREY
    }
}