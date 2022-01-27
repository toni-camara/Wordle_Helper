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


class search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var noResultsListed: List<String> = listOf("There's no coincidence")
        var filteredList: List<String> = listOf()
        var wordList: MutableList<String> = mutableListOf()

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
            var input6Text = findViewById<EditText>(R.id.input6)
            input6Text.setText("")
            var input7Text = findViewById<EditText>(R.id.input7)
            input7Text.setText("")
            var input8Text = findViewById<EditText>(R.id.input8)
            input8Text.setText("")
            var input9Text = findViewById<EditText>(R.id.input9)
            input9Text.setText("")
            var input10Text = findViewById<EditText>(R.id.input10)
            input10Text.setText("")
            var input11Text = findViewById<EditText>(R.id.input11)
            input11Text.setText("")
            var input12Text = findViewById<EditText>(R.id.input12)
            input12Text.setText("")
            var input13Text = findViewById<EditText>(R.id.input13)
            input13Text.setText("")
            var input14Text = findViewById<EditText>(R.id.input14)
            input14Text.setText("")
            var input15Text = findViewById<EditText>(R.id.input15)
            input15Text.setText("")
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

            val input6 = findViewById<View>(R.id.input6) as EditText
            var input6Text: Char?
            if (input6.text.toString().equals("")) {
                input6Text = null
            } else {
                input6Text = input6.text.single()
            }

            val input7 = findViewById<View>(R.id.input7) as EditText
            var input7Text: Char?
            if (input7.text.toString().equals("")) {
                input7Text = null
            } else {
                input7Text = input7.text.single()
            }

            val input8 = findViewById<View>(R.id.input8) as EditText
            var input8Text: Char?
            if (input8.text.toString().equals("")) {
                input8Text = null
            } else {
                input8Text = input8.text.single()
            }

            val input9 = findViewById<View>(R.id.input9) as EditText
            var input9Text: Char?
            if (input9.text.toString().equals("")) {
                input9Text = null
            } else {
                input9Text = input9.text.single()
            }

            val input10 = findViewById<View>(R.id.input10) as EditText
            var input10Text: Char?
            if (input10.text.toString().equals("")) {
                input10Text = null
            } else {
                input10Text = input10.text.single()
            }

            val input11 = findViewById<View>(R.id.input11) as EditText
            var input11Text: Char?
            if (input11.text.toString().equals("")) {
                input11Text = null
            } else {
                input11Text = input11.text.single()
            }

            val input12 = findViewById<View>(R.id.input12) as EditText
            var input12Text: Char?
            if (input12.text.toString().equals("")) {
                input12Text = null
            } else {
                input12Text = input12.text.single()
            }

            val input13 = findViewById<View>(R.id.input13) as EditText
            var input13Text: Char?
            if (input13.text.toString().equals("")) {
                input13Text = null
            } else {
                input13Text = input13.text.single()
            }

            val input14 = findViewById<View>(R.id.input14) as EditText
            var input14Text: Char?
            if (input14.text.toString().equals("")) {
                input14Text = null
            } else {
                input14Text = input14.text.single()
            }

            val input15 = findViewById<View>(R.id.input15) as EditText
            var input15Text: Char?
            if (input15.text.toString().equals("")) {
                input15Text = null
            } else {
                input15Text = input15.text.single()
            }
//TODO
            var input: List<input> =
                listOf(
                    input(input1Text?.lowercaseChar(), input.letterColor.GREEN),
                    input(input2Text?.lowercaseChar(), input.letterColor.GREEN),
                    input(input3Text?.lowercaseChar(), input.letterColor.GREEN),
                    input(input4Text?.lowercaseChar(), input.letterColor.GREEN),
                    input(input5Text?.lowercaseChar(), input.letterColor.GREEN),
                    input(input6Text?.lowercaseChar(), input.letterColor.YELLOW),
                    input(input7Text?.lowercaseChar(), input.letterColor.YELLOW),
                    input(input8Text?.lowercaseChar(), input.letterColor.YELLOW),
                    input(input9Text?.lowercaseChar(), input.letterColor.YELLOW),
                    input(input10Text?.lowercaseChar(), input.letterColor.YELLOW),
                    input(input11Text?.lowercaseChar(), input.letterColor.BLACK),
                    input(input12Text?.lowercaseChar(), input.letterColor.BLACK),
                    input(input13Text?.lowercaseChar(), input.letterColor.BLACK),
                    input(input14Text?.lowercaseChar(), input.letterColor.BLACK),
                    input(input15Text?.lowercaseChar(), input.letterColor.BLACK),
                )


            //if (wordList.isNotEmpty()) { // Verifies that the list has been obtained from the file

            wordList = myMethods().readWordsFromFile(this@search).toMutableList()
            filteredList = myMethods().searchForInput(wordList, input)
            if (filteredList.isEmpty()) myMethods().drawWordList(noResultsListed, this)
            else myMethods().drawWordList(filteredList, this)

            //}
        }

        /** Click on Input 1*/
        val input1 = findViewById<EditText>(R.id.input1)
        input1.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input1.setText("")
                input1.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input1.setBackgroundColor(resources.getColor(R.color.secundario))
                else input1.setBackgroundColor(resources.getColor(R.color.verde)) // #6200EE
            }
        })

        /** Click on Input 2*/
        val input2 = findViewById<EditText>(R.id.input2)
        input2.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input2.setText("")
                input2.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input2.setBackgroundColor(resources.getColor(R.color.secundario))
                else input2.setBackgroundColor(resources.getColor(R.color.verde)) // #6200EE
            }
        })

        /** Click on Input 3*/
        val input3 = findViewById<EditText>(R.id.input3)
        input3.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input3.setText("")
                input3.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input3.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input3.setBackgroundColor(resources.getColor(R.color.verde)) // #6200EE
            }
        })

        /** Click on Input 4*/
        val input4 = findViewById<EditText>(R.id.input4)
        input4.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input4.setText("")
                input4.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input4.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input4.setBackgroundColor(resources.getColor(R.color.verde)) // #6200EE
            }
        })

        /** Click on Input 5*/
        val input5 = findViewById<EditText>(R.id.input5)
        input5.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input5.setText("")
                input5.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input5.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input5.setBackgroundColor(resources.getColor(R.color.verde)) // #6200EE
            }
        })

        /** Click on Input 6*/
        val input6 = findViewById<EditText>(R.id.input6)
        input6.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input6.setText("")
                input6.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input6.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input6.setBackgroundColor(resources.getColor(R.color.amarillo)) // #6200EE
            }
        })

        /** Click on Input 7*/
        val input7 = findViewById<EditText>(R.id.input7)
        input7.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input7.setText("")
                input7.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input7.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input7.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input7.setBackgroundColor(resources.getColor(R.color.amarillo)) // #6200EE
            }
        })

        /** Click on Input 8*/
        val input8 = findViewById<EditText>(R.id.input8)
        input8.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input8.setText("")
                input8.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input8.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input8.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input8.setBackgroundColor(resources.getColor(R.color.amarillo)) // #6200EE
            }
        })

        /** Click on Input 9*/
        val input9 = findViewById<EditText>(R.id.input9)
        input9.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input9.setText("")
                input9.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input9.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input9.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input9.setBackgroundColor(resources.getColor(R.color.amarillo)) // #6200EE
            }
        })

        /** Click on Input 10*/
        val input10 = findViewById<EditText>(R.id.input10)
        input10.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input10.setText("")
                input10.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input10.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input10.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input10.setBackgroundColor(resources.getColor(R.color.amarillo)) // #6200EE
            }
        })

        /** Click on Input 11*/
        val input11 = findViewById<EditText>(R.id.input11)
        input11.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input11.setText("")
                input11.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input11.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input11.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input11.setBackgroundColor(resources.getColor(R.color.negro)) // #6200EE
            }
        })

        /** Click on Input 12*/
        val input12 = findViewById<EditText>(R.id.input12)
        input12.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input12.setText("")
                input12.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input12.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input12.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input12.setBackgroundColor(resources.getColor(R.color.negro)) // #6200EE
            }
        })

        /** Click on Input 13*/
        val input13 = findViewById<EditText>(R.id.input13)
        input13.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input13.setText("")
                input13.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input13.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input13.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input13.setBackgroundColor(resources.getColor(R.color.negro)) // #6200EE
            }
        })

        /** Click on Input 14*/
        val input14 = findViewById<EditText>(R.id.input14)
        input14.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input14.setText("")
                input14.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input14.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input14.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input14.setBackgroundColor(resources.getColor(R.color.negro)) // #6200EE
            }
        })

        /** Click on Input 15*/
        val input15 = findViewById<EditText>(R.id.input15)
        input15.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                input15.setText("")
                input15.setBackgroundColor(resources.getColor(R.color.secundario))
            }
        }

        input15.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                hideKeyboard()
                if (s.toString().equals("")) input15.setBackgroundColor(resources.getColor(R.color.design_default_color_primary))
                else input15.setBackgroundColor(resources.getColor(R.color.negro)) // #6200EE
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