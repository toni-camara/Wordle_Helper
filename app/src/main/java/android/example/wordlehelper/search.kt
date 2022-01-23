package android.example.wordlehelper

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView


class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var listIsFull: Boolean = false
        var filteredListIsFull: Boolean = false
        var filteredList: List<String> = listOf()
        val wordList = mutableListOf<String>()

        /** Click on SHOW LIST Button*/
        val showListButton = findViewById<Button>(R.id.showListBtn)
        showListButton.setOnClickListener {

            var completeList: List<String> = myMethods().readWordsFromFile(this@search)
            findViewById<LinearLayout>(R.id.verticalWordsLeft).removeAllViews()
            findViewById<LinearLayout>(R.id.verticalWordsRight).removeAllViews()

            var lineCounter = 1 // Used to count a maximum of 4 lines of words

            for (word in completeList) {
                if (!listIsFull) wordList.add(word) // Protection against duplicity

                // Word list settings
                val vistaNueva = TextView(this)
                vistaNueva.textSize = 20f
                vistaNueva.text = word.uppercase()
                vistaNueva.setTextColor(Color.parseColor("#6746c3"))
                val typeface = Typeface.SANS_SERIF
                vistaNueva.setTypeface(typeface)

                //Word list printing
                if (lineCounter <= 4) {
                    findViewById<LinearLayout>(R.id.verticalWordsLeft).addView(vistaNueva)
                } else if (lineCounter <= 8) {
                    findViewById<LinearLayout>(R.id.verticalWordsRight).addView(vistaNueva)
                }
                lineCounter++
            }

            listIsFull = true
        }

        /** Click on FILTER LETTER Button*/
        val filterButton = findViewById<Button>(R.id.filterBtn)
        filterButton.setOnClickListener {

            if (wordList.isNotEmpty()) { // Verifies that the list has been obtained from the file
                var input: String = "r" //Placeholder Search Criteria

                if (!filteredListIsFull) filteredList = myMethods().searchForInput(wordList, input)

                findViewById<LinearLayout>(R.id.verticalWordsLeft).removeAllViews()
                findViewById<LinearLayout>(R.id.verticalWordsRight).removeAllViews()

                var lineCounter = 1
                for (word in filteredList) {

                    // Word list settings
                    val vistaNueva = TextView(this)
                    vistaNueva.textSize = 20f
                    vistaNueva.text = word.uppercase()
                    vistaNueva.setTextColor(Color.parseColor("#6746c3"))
                    val typeface =
                        Typeface.SANS_SERIF//createFromAsset(applicationContext.assets, "sans-serif-light")
                    vistaNueva.setTypeface(typeface)

                    //Word list printing
                    if (lineCounter <= 4) {
                        findViewById<LinearLayout>(R.id.verticalWordsLeft).addView(vistaNueva)
                    } else if (lineCounter <= 8) {
                        findViewById<LinearLayout>(R.id.verticalWordsRight).addView(vistaNueva)
                    }
                    lineCounter++
                }
                filteredListIsFull = true

            }
        }
    }
}