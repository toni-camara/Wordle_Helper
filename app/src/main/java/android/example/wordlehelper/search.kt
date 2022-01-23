package android.example.wordlehelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val wordList = mutableListOf<String>()


        val showListButton = findViewById<Button>(R.id.showListBtn)
        showListButton.setOnClickListener {
            var texto: String = myMethods().readWordsFromFile(this@search).joinToString { "\n" }
            findViewById<TextView>(R.id.word1).text = texto

        }
    }
}