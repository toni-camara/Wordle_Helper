package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.marginTop


class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val wordList = mutableListOf<String>()


        val showListButton = findViewById<Button>(R.id.showListBtn)
        showListButton.setOnClickListener {
            //var texto: String = myMethods().readWordsFromFile(this@search).joinToString { "\n" }
            var texto:List<String> = myMethods().readWordsFromFile(this@search)
            var lineCounter = 1

            for(word in texto) {
                val vistaNueva = TextView(this)
                vistaNueva.textSize = 20f
                vistaNueva.text = word


                if(lineCounter <= 4) {
                    findViewById<LinearLayout>(R.id.verticalWordsLeft).addView(vistaNueva)
                }
                else if(lineCounter <=8) {
                    findViewById<LinearLayout>(R.id.verticalWordsRight).addView(vistaNueva)
                }
                lineCounter ++
            }

        }
    }
}