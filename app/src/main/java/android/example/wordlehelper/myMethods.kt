package android.example.wordlehelper

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import java.io.IOException


class myMethods {

    /** This method draws the list of words */
    fun drawWordList(filteredList: List<String>, Activity: Activity) {
        Activity.findViewById<LinearLayout>(R.id.verticalWordsLeft).removeAllViews()
        Activity.findViewById<LinearLayout>(R.id.verticalWordsRight).removeAllViews()

        var lineCounter = 1
        for (word in filteredList) {

            // Word list settings
            val vistaNueva = TextView(Activity)
            vistaNueva.textSize = 20f
            vistaNueva.text = word.uppercase()
            vistaNueva.setTextColor(Color.parseColor("#6746c3"))
            val typeface =
                Typeface.SANS_SERIF//createFromAsset(applicationContext.assets, "sans-serif-light")
            vistaNueva.setTypeface(typeface)

            //Word list printing
            if (lineCounter <= 4) {
                Activity.findViewById<LinearLayout>(R.id.verticalWordsLeft).addView(vistaNueva)
            } else if (lineCounter <= 8) {
                Activity.findViewById<LinearLayout>(R.id.verticalWordsRight).addView(vistaNueva)
            }
            lineCounter++
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
    fun searchForInput(wordList: List<String>, input: String): List<String> {
        val wordsWithInput = mutableListOf<String>()
        println("Your input was: $input . looking for words containing $input...\n")

        for (word in wordList) {
            for (letter in word.lowercase()) {
                if (input == letter.toString()) {
                    wordsWithInput.add(word)
                }
            }
        }
        wordsWithInput.forEach { println(it) }
        return wordsWithInput
    }


}

