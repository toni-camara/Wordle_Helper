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
            val intent = Intent(this, Game::class.java)
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
            val intent = Intent(this, Learn::class.java)
            startActivity(intent)
        }

    }
}

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
            val intent = Intent(activity, Game::class.java)
            startActivity(intent)
        }

        //CODE BUTTON
        val codeButton = view.findViewById<Button>(R.id.codeBtn)
        codeButton.setOnClickListener {
            val intent = Intent(activity, Code::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

}