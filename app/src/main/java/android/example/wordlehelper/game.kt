package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /** INITIAL ACTIVE WORD TEXTVIEWS */

        var letraActiva = findViewById<View>(R.id.Guess11) as TextView
        letraActiva.requestFocus()
        val activeWord = findViewById<LinearLayout>(R.id.wordsContainerOverlay)
        for(row in 0 until activeWord.childCount){
            val wordRow = activeWord.getChildAt(row) as LinearLayout
            for (casilla in 0 until wordRow.childCount){
                val letter = wordRow.getChildAt(casilla) as TextView
                letter.addTextChangedListener(object : TextWatcher{
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        val parent = letter.parent as LinearLayout
                        val letraActivaIndex = parent.indexOfChild(letter)
                        val nextLetraActiva = parent.getChildAt(letraActivaIndex + 1)
                        if (letraActivaIndex != parent.childCount-1) nextLetraActiva.requestFocus()



                    }
                })
            }

        }


        /** KEYBOARD BUTTONS FUNCTIONS */

        val verticalLayout = findViewById<LinearLayout>(R.id.keyboardContainerLayout)
        for (row in 0 until verticalLayout.childCount){
            val keyboardRow = verticalLayout.getChildAt(row) as LinearLayout
            for (button in 0 until keyboardRow.childCount) {
                val key = keyboardRow.getChildAt(button) as Button
                if(key.text.toString() != "ENTER" && key.text.toString() != "DEL"){
                    //HACER COSAS CON LOS BOTONES AQUI
                    key.setOnClickListener {
                        val letraActiva = currentFocus as TextView
                        letraActiva.text = key.text

                    }

                }

            }
        }




    }
}