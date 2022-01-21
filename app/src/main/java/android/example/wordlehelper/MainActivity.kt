package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import java.io.BufferedReader
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

fun main(args: Array<String>) {
    val lista = arrayOf("cebar", "Cobra", "Robot", "Morir")
    var usosLetra = IntArray(26)

    println("La lista contiene ${lista.size} palabras y son:")

    for(word in lista) {
        var minuscula: String = word.lowercase()
        for(char in minuscula){
                usosLetra[(char.code -97)]++
        }

        println("$word")
    }

    println("Los usos de cada letra del alfabeto en la lista son los siguientes:")

    for(i in 0 until usosLetra.size){
        if (usosLetra[i] != 0) {
            println("${(i + 97).toChar()}: usada ${usosLetra[i]} veces")
        }
    }



}

