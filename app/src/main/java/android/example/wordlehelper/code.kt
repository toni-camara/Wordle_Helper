package android.example.wordlehelper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Code : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)

        //READ CODE BUTTON PRESS
        val readCode = findViewById<Button>(R.id.codeBtn)
        readCode.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://github.com/toni-camara/Wordle_Helper")
            startActivity(i)
        }
    }
}