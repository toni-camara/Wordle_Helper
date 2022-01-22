package android.example.wordlehelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

fun main() {
    val wordList = mutableListOf<String>()

    listFromFile(wordList)

    for (entry in mostUsedLetters(wordList)) {
        println("${entry.key}: usada ${entry.value} veces")
    }

    wordList.sort()
    wordList.forEach{println(it)}

    println("Please input character: ") // User Input
    val charInput = readLine()!!

    for (entry in searchForInput(wordList, charInput)) {
        println(entry.key)
    }
}

fun listFromFile(wordList: MutableList<String>){
    val file = File("app\\src\\main\\assets\\example.txt")
    file.forEachLine { wordList.add(it) }  // Store each line from the file into an element of the List
}

/** This method takes a Character input and searches the list for words containing that input */
fun searchForInput(wordList: List<String>, input: String): Map<String, Int> {
    val letterUses = mutableMapOf<String, Int>()

    for (word in wordList) {
        for (letter in word.lowercase()) {
            if (input == letter.toString()) {
                letterUses[word] = (letterUses[word] ?: 0) + 1 // ?: 0 is anti-Null protection
            }
        }
    }

    println("Your input was: $input . looking for words containing $input...\n")
    return letterUses.entries.sortedByDescending { it.value }.associate { it.toPair() }
}

/** This method takes the word list and returns the most used letters, sorted by descending order*/
fun mostUsedLetters(wordList: List<String>): Map<Char, Int> {
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

