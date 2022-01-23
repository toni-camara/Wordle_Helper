package android.example.wordlehelper

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException
import android.widget.RelativeLayout
import androidx.core.view.marginTop


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

