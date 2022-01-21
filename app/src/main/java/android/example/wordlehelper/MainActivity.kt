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
    val usosLetra = hashMapOf<Char, Int>()


    println("La lista contiene ${lista.size} palabras y son:")

    for(word in lista) {
        var minuscula: String = word.lowercase()
        for(letra in minuscula){
                var cantidad = usosLetra.get(letra) ?: 0  // ?: 0 es proteccion anti Null
                usosLetra.put(letra, cantidad + 1)
        }

        println("$word")
    }

    println("Los usos de cada letra del alfabeto en la lista son los siguientes:")

    val usosLetraOrdenado = usosLetra.entries.sortedByDescending { it.value }


    for(entry in usosLetraOrdenado){
        println("${entry.key}: usada ${entry.value} veces")
        }



}

