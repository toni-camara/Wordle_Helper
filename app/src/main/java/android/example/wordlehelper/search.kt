package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText


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

            val input1 = findViewById<View>(R.id.input1) as EditText
            var input1Text: Char?
            if (input1.text.toString().equals("")) {
                input1Text = null
            } else {
                input1Text = input1.text.single()
            }

            val input2 = findViewById<View>(R.id.input2) as EditText
            var input2Text: Char?
            if (input2.text.toString().equals("")) {
                input2Text = null
            } else {
                input2Text = input2.text.single()
            }

            val input3 = findViewById<View>(R.id.input3) as EditText
            var input3Text: Char?
            if (input3.text.toString().equals("")) {
                input3Text = null
            } else {
                input3Text = input3.text.single()
            }

            val input4 = findViewById<View>(R.id.input4) as EditText
            var input4Text: Char?
            if (input4.text.toString().equals("")) {
                input4Text = null
            } else {
                input4Text = input4.text.single()
            }

            val input5 = findViewById<View>(R.id.input5) as EditText
            var input5Text: Char?
            if (input5.text.toString().equals("")) {
                input5Text = null
            } else {
                input5Text = input5.text.single()
            }

            var input: List<Char?> =
                listOf(input1Text, input2Text, input3Text, input4Text, input5Text)


            if (wordList.isNotEmpty()) { // Verifies that the list has been obtained from the file
                // var input: List<Char?> =
                //     listOf('r', null, 'm', null, null) //Placeholder Search Criteria

                if (!searchIsDone) filteredList = myMethods().searchForInput(wordList, input)
                myMethods().drawWordList(filteredList, this)
                searchIsDone = true
            }
        }

    }
}
