package android.example.wordlehelper

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class ModalBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileBtn = view.findViewById<Button>(R.id.profileBtn)
        profileBtn.setOnClickListener {
            val intent = Intent(activity, game::class.java)
            startActivity(intent)
        }

        //CODE BUTTON
        val codeButton = view.findViewById<Button>(R.id.codeBtn)
        codeButton.setOnClickListener {
            val intent = Intent(activity, code::class.java)
            startActivity(intent)
        }

    }





    companion object {
        const val TAG = "ModalBottomSheet"
    }

}

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //BOTTOM SHEET
        val modalBottomSheet = ModalBottomSheet()
        val bottomSheetButton = findViewById<Button>(R.id.bottomSheetExpandButton)
        bottomSheetButton.setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
        }


        //PLAY BUTTON
        val playButton = findViewById<Button>(R.id.playBtn)
        playButton.setOnClickListener {
            val intent = Intent(this, game::class.java)
            startActivity(intent)
        }

        //SEARCH BUTTON
        val searchButton = findViewById<Button>(R.id.searchBtn)
        searchButton.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
        }

        //LEARN BUTTON
        val learnButton = findViewById<Button>(R.id.learnBtn)
        learnButton.setOnClickListener {
            val intent = Intent(this, learn::class.java)
            startActivity(intent)
        }


    }
}

fun main() {
    val wordList = mutableListOf<String>()
    readWordsFromFile(wordList)
    printWordsFromList(wordList)
    mostUsedLetters(wordList)

    val letterInput = readInput()
    searchForInput(wordList, letterInput)
}

/** This method takes a list of words from a file and stores them into a list */
fun readWordsFromFile(wordList: MutableList<String>) {
    val file = File("app\\src\\main\\assets\\example.txt")
    file.forEachLine { wordList.add(it) }  // Store each line from the file into an element of the List
    wordList.sort()
}

/** This method takes a list of words and prints them */
fun printWordsFromList(wordList: MutableList<String>) {
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

    val letterFrequency =
        letterUses.entries.sortedByDescending { it.value }.associate { it.toPair() }
    letterUses.toSortedMap()

    for (entry in letterFrequency) {
        println("${entry.key}: usada ${entry.value} veces")
    }
}

/** This method asks the user for a character input*/
fun readInput(): String {
    println("Please input character: ") // User Input
    return readLine()!!
}

/** This method takes a Character input and searches the list for words containing that input */
fun searchForInput(wordList: List<String>, input: String) {
    val wordsWithInput = mutableListOf<String>()
    println("Your input was: $input . looking for words containing $input...\n")

    for (word in wordList) {
        for (letter in word.lowercase()) {
            if (input == letter.toString()) {
                wordsWithInput.add(word)
            }
        }
    }
    wordsWithInput.forEach { println(it) }
}