package android.example.wordlehelper

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

class myMethods {

    /** This method takes a list of words from a file and stores them into a list */
    fun readWordsFromFile(context: Context): List<String>{
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

}

