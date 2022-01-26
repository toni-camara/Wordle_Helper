package android.example.wordlehelper

import android.example.wordlehelper.letterColor.*
import org.junit.Assert.assertEquals
import org.junit.Test

class myMethodsTest {
    @Test
    fun middleLetterIsA() {
        val mutableList = mutableListOf<String>("cebar","koala", "nitro","12345")
        val inputLetter = 'a'
        val posicion = 2

        val resultado = myMethods().deleteRestOfWords(mutableList,inputLetter,posicion)

        val expectedResult = mutableListOf("koala")
        assertEquals(expectedResult, resultado)

    }

    @Test
    fun firstLetterIsY() {
        val mutableList = mutableListOf<String>("cebar","koala", "nitro","12345")
        val inputLetter = 'Y'
        val posicion = 0

        val resultado = myMethods().deleteRestOfWords(mutableList,inputLetter,posicion)

        val expectedResult = mutableListOf<String>()
        assertEquals(expectedResult, resultado)

    }

    @Test
    fun searchForInputTestYellow(){
        val wordList = mutableListOf("cebar","koala", "nitro","arbol","eyato",)
        val input = mapOf('y' to YELLOW,null to GREY,'a' to GREEN,null to GREY,null to GREY)

        val resultado = myMethods().searchForInput(wordList, input)
        val expectedResult = listOf("koala","eyato")
        assertEquals(expectedResult, resultado)
    }
}