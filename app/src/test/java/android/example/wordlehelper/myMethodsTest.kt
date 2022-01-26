package android.example.wordlehelper

import android.example.wordlehelper.input.letterColor.*
import org.junit.Assert.assertEquals
import org.junit.Test

class myMethodsTest {
    @Test
    fun middleLetterIsA() {
        val mutableList = mutableListOf<String>("cebar", "koala", "nitro", "12345")
        val inputLetter = 'a'
        val posicion = 2

        val resultado = myMethods().deleteRestOfWords(mutableList, inputLetter, posicion)

        val expectedResult = mutableListOf("koala")
        assertEquals(expectedResult, resultado)

    }

    @Test
    fun firstLetterIsY() {
        val mutableList = mutableListOf<String>("cebar", "koala", "nitro", "12345")
        val inputLetter = 'Y'
        val posicion = 0

        val resultado = myMethods().deleteRestOfWords(mutableList, inputLetter, posicion)

        val expectedResult = mutableListOf<String>()
        assertEquals(expectedResult, resultado)

    }

    @Test
    fun searchForInputTestGreen() {
        val wordList = mutableListOf("cebar", "koala", "nitro", "arbol", "eyato")
        val input = listOf(
            input(null, GREY),
            input(null, GREY),
            input('a', GREEN),
            input(null, GREY),
            input(null, GREY)
        )

        val resultado = myMethods().searchForInput(wordList, input)
        val expectedResult = listOf("eyato", "koala")
        assertEquals(expectedResult, resultado)
    }


    @Test
    fun searchForInputTestYellow() {
        val wordList = mutableListOf("cebar", "koala", "nitro", "tiara", "arbol", "eyato")
        val input = listOf(
            input('y', YELLOW),
            input(null, GREY),
            input('a', GREEN),
            input(null, GREY),
            input(null, GREY)
        )

        val resultado = myMethods().searchForInput(wordList, input)
        val expectedResult = listOf("eyato")
        assertEquals(expectedResult, resultado)
    }

    @Test
    fun searchForInputTestBlack() {
        val wordList = mutableListOf("cebar", "koala", "nitro", "tiara", "arbol", "eyato", "eyaio")
        val input = listOf(
            input('y', YELLOW),
            input(null, GREY),
            input('a', GREEN),
            input(null, GREY),
            input(null, GREY),
            input('i', BLACK)
        )

        val resultado = myMethods().searchForInput(wordList, input)
        val expectedResult = listOf("eyato")
        assertEquals(expectedResult, resultado)
    }
}