package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var searchIsDone: Boolean = false
        var filteredList: List<String> = listOf()
        var wordList: MutableList<String> = mutableListOf()

        /** Click on SHOW LIST Button*/
        val showListButton = findViewById<Button>(R.id.showListBtn)
        showListButton.setOnClickListener {
            wordList.clear()
            wordList = myMethods().readWordsFromFile(this@search).toMutableList()
            myMethods().drawWordList(wordList, this)
        }

        /** Click on FILTER LETTER Button*/
        val filterButton = findViewById<Button>(R.id.filterBtn)
        filterButton.setOnClickListener {

            if (wordList.isNotEmpty()) { // Verifies that the list has been obtained from the file
                var input: List<Char?> =
                    listOf('r', null, 'm', null, null) //Placeholder Search Criteria

                if (!searchIsDone) filteredList = myMethods().searchForInput(wordList, input)
                myMethods().drawWordList(filteredList, this)
                searchIsDone = true
            }
        }

    }
}
