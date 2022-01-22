package android.example.wordlehelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

fun main() {
    val wordList = mutableListOf("cebar", "Cobra", "Robot", "Morir", "riñon")

    for (entry in mostUsedWords(wordList)) {
        println("${entry.key}: usada ${entry.value} veces")
    }

    println("Please input character: ") // User Input
    val charInput = readLine()!!

    for (entry in searchForInput(wordList, charInput)) {
        println(entry.key)
    }
}

/** This method takes a Character input and searches the list for words containing that input */
fun searchForInput(wordList: List<String>, input: String): Map<String, Int> {
    val letterUses = mutableMapOf<String, Int>()

    for (word in wordList) {
        for (letter in word.lowercase()) {
            if (input == letter.toString()) {
                letterUses[word] = (letterUses[word] ?: 0) + 1 // ?: 0 es proteccion anti Null
            }
        }
    }

    println("Your input was: $input . looking for words containing $input...\n")
    return letterUses.entries.sortedByDescending { it.value }.associate { it.toPair() }
}

/** This method takes the word list and returns the most used letters, sorted by descending order*/
fun mostUsedWords(wordList: List<String>): Map<Char, Int> {
    val letterUses = mutableMapOf<Char, Int>()
    println("La lista contiene ${wordList.size} palabras y son:")

    for (word in wordList) {
        for (letra in word.lowercase()) {
            letterUses[letra] = (letterUses[letra] ?: 0) + 1 // ?: 0 es proteccion anti Null
        }
        println(word)
    }

    println("Los usos de cada letra del alfabeto en la lista son los siguientes:")
    return letterUses.entries.sortedByDescending { it.value }.associate { it.toPair() }
}

