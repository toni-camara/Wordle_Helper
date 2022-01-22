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
    readWordsFromFile(wordList)
    printWordsFromList(wordList)
    mostUsedLetters(wordList)

    val letterInput = readInput()
    searchForInput(wordList,letterInput)
}

/** This method takes a list of words from a file and stores them into a list */
fun readWordsFromFile(wordList: MutableList<String>){
    val file = File("app\\src\\main\\assets\\example.txt")
    file.forEachLine { wordList.add(it) }  // Store each line from the file into an element of the List
    wordList.sort()
}

/** This method takes a list of words and prints them */
fun printWordsFromList(wordList: MutableList<String>){
    println("La lista contiene ${wordList.size} palabras y son:")
    wordList.forEach { println(it) }
}

/** This method takes a word list and returns the most used letters, sorted by descending order*/
fun mostUsedLetters(wordList: List<String>) {
    val letterUses = mutableMapOf<Char, Int>()

    for (word in wordList) {
        for (letra in word.lowercase()) {
            letterUses[letra] = (letterUses[letra] ?: 0) + 1 // ?: 0 es proteccion anti Null
        }
    }

    println("Los usos de cada letra del alfabeto en la lista son los siguientes:")

    val letterFrequency = letterUses.entries.sortedByDescending { it.value }.associate { it.toPair() }
    letterUses.toSortedMap()

    for (entry in letterFrequency) {
        println("${entry.key}: usada ${entry.value} veces")
    }
}

/** This method asks the user for a character input*/
fun readInput(): String{
    println("Please input character: ") // User Input
    return readLine()!!
}

/** This method takes a Character input and searches the list for words containing that input */
fun searchForInput(wordList: List<String>, input: String) {
    val wordsWithInput = mutableMapOf<String, Int>()

    for (word in wordList) {
        for (letter in word.lowercase()) {
            if (input == letter.toString()) {
                wordsWithInput[word] = (wordsWithInput[word] ?: 0) + 1 // ?: 0 is anti-Null protection
            }
        }
    }

    println("Your input was: $input . looking for words containing $input...\n")

    for (entry in wordsWithInput) {
        println(entry.key)
    }
}