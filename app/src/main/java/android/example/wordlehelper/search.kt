package android.example.wordlehelper

import android.app.Activity
import android.content.Context
//import android.example.wordlehelper.MyUtilities.hideKeyboard
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.view.View.OnFocusChangeListener





class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var filteredList: List<String> = listOf()
        var wordList: MutableList<String> = mutableListOf()



        /** Color change on Input 1*/
        val input1Color = findViewById<EditText>(R.id.input1)
         input1Color.setOnFocusChangeListener { v, hasFocus ->
             if (hasFocus) input1Color.setBackgroundColor(resources.getColor(R.color.secundario)) // #6200EE
         }
                /** Color change on Input 2*/
        val input2Color = findViewById<EditText>(R.id.input2)
         input2Color.setOnFocusChangeListener { v, hasFocus ->
             if (hasFocus) input2Color.setBackgroundColor(resources.getColor(R.color.secundario)) // #6200EE
         }
                /** Color change on Input 3*/
        val input3Color = findViewById<EditText>(R.id.input3)
         input3Color.setOnFocusChangeListener { v, hasFocus ->
             if (hasFocus) input3Color.setBackgroundColor(resources.getColor(R.color.secundario)) // #6200EE
         }
                /** Color change on Input 4*/
        val input4Color = findViewById<EditText>(R.id.input4)
         input4Color.setOnFocusChangeListener { v, hasFocus ->
             if (hasFocus) input4Color.setBackgroundColor(resources.getColor(R.color.secundario)) // #6200EE
         }
                /** Color change on Input 5*/
        val input5Color = findViewById<EditText>(R.id.input5)
         input5Color.setOnFocusChangeListener { v, hasFocus ->
             if (hasFocus) input5Color.setBackgroundColor(resources.getColor(R.color.secundario)) // #6200EE
         }




        /** Click on SHOW LIST Button*/
        val showListButton = findViewById<Button>(R.id.showListBtn)
        showListButton.setOnClickListener {
            wordList.clear()
            var input1Text = findViewById<EditText>(R.id.input1)
            input1Text.setText("")
            var input2Text = findViewById<EditText>(R.id.input2)
            input2Text.setText("")
            var input3Text = findViewById<EditText>(R.id.input3)
            input3Text.setText("")
            var input4Text = findViewById<EditText>(R.id.input4)
            input4Text.setText("")
            var input5Text = findViewById<EditText>(R.id.input5)
            input5Text.setText("")
            //wordList = myMethods().readWordsFromFile(this@search).toMutableList()
            myMethods().drawWordList(wordList, this)
        }

        /** Click on FILTER LETTER Button*/
        val filterButton = findViewById<Button>(R.id.filterBtn)
        filterButton.setOnClickListener {

            val input1 = findViewById<View>(R.id.input1) as EditText
            var input1Text: Char?
            if (input1.text.toString().equals("")) {
                input1Text = null
            } else {
                input1Text = input1.text.single()
            }

            val input2 = findViewById<View>(R.id.input2) as EditText
            var input2Text: Char?
            if (input2.text.toString().equals("")) {
                input2Text = null
            } else {
                input2Text = input2.text.single()
            }

            val input3 = findViewById<View>(R.id.input3) as EditText
            var input3Text: Char?
            if (input3.text.toString().equals("")) {
                input3Text = null
            } else {
                input3Text = input3.text.single()
            }

            val input4 = findViewById<View>(R.id.input4) as EditText
            var input4Text: Char?
            if (input4.text.toString().equals("")) {
                input4Text = null
            } else {
                input4Text = input4.text.single()
            }

            val input5 = findViewById<View>(R.id.input5) as EditText
            var input5Text: Char?
            if (input5.text.toString().equals("")) {
                input5Text = null
            } else {
                input5Text = input5.text.single()
            }

            var input: List<Char?> =
                listOf(
                    input1Text?.lowercaseChar(),
                    input2Text?.lowercaseChar(),
                    input3Text?.lowercaseChar(),
                    input4Text?.lowercaseChar(),
                    input5Text?.lowercaseChar()
                )


            //if (wordList.isNotEmpty()) { // Verifies that the list has been obtained from the file

            wordList = myMethods().readWordsFromFile(this@search).toMutableList()
            filteredList = myMethods().searchForInput(wordList, input)
            myMethods().drawWordList(filteredList, this)

            //}
        }

        /** Change of text on Input 1*/
        val input1 = findViewById<EditText>(R.id.input1)
        input1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                input1Color.setBackgroundColor(resources.getColor(R.color.design_default_color_secondary)) // #6200EE

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                input1Color.setBackgroundColor(resources.getColor(R.color.design_default_color_primary)) // #6200EE
            }
        })

        /** Change of text on Input 2*/
        val input2 = findViewById<EditText>(R.id.input2)
        input2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                input2Color.setBackgroundColor(resources.getColor(R.color.design_default_color_primary)) // #6200EE
            }
        })

        /** Change of text on Input 3*/
        val input3 = findViewById<EditText>(R.id.input3)
        input3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                input3Color.setBackgroundColor(resources.getColor(R.color.design_default_color_primary)) // #6200EE
            }
        })

        /** Change of text on Input 4*/
        val input4 = findViewById<EditText>(R.id.input4)
        input4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                input4Color.setBackgroundColor(resources.getColor(R.color.design_default_color_primary)) // #6200EE
            }
        })

        /** Change of text on Input 5*/
        val input5 = findViewById<EditText>(R.id.input5)
        input5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                input5Color.setBackgroundColor(resources.getColor(R.color.design_default_color_primary)) // #6200EE
            }
        })

    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}

/** internal object MyUtilities {
fun Fragment.hideKeyboard() {
view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
}*/