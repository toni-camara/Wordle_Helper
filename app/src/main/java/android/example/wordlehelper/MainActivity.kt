package android.example.wordlehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File
import java.io.BufferedReader
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

fun main(args: Array<String>) {
    val palabras = mutableListOf("cebar", "Cobra", "Robot", "Morir", "riñon")

    for (entry in letrasMasUsadas(palabras)) {
        println("${entry.key}: usada ${entry.value} veces")
    }

    println("Please input character: ")
    var charInput = readLine()!!


    for (entry in buscarLetra(palabras, charInput)) {
        println("${entry.key}")
    }




}

fun buscarLetra(lista: List<String>, input: String): Map<String, Int> {

    val letraUsada = mutableMapOf<String, Int>()


    for (word in lista) {
        for (letra in word.lowercase()) {
            if (input == letra.toString()) {
                letraUsada[word] = (letraUsada[word] ?: 0) + 1 // ?: 0 es proteccion anti Null
            }

        }

    }

    println("Your input was: $input . looking for words containing $input...\n")

    val usosLetraOrdenado = letraUsada.entries.sortedByDescending { it.value }.associate { it.toPair() }
    return usosLetraOrdenado


}

fun letrasMasUsadas(lista: List<String>): Map<Char, Int> {

    val usosLetra = mutableMapOf<Char, Int>()


    println("La lista contiene ${lista.size} palabras y son:")

    for (word in lista) {
            for (letra in word.lowercase()) {
            usosLetra[letra] = (usosLetra[letra] ?: 0) + 1 // ?: 0 es proteccion anti Null
        }

        println("$word")
    }

    println("Los usos de cada letra del alfabeto en la lista son los siguientes:")

    val usosLetraOrdenado = usosLetra.entries.sortedByDescending { it.value }.associate { it.toPair() }
    return usosLetraOrdenado


}

