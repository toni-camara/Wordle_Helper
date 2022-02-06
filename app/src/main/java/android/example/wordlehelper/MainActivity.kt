package android.example.wordlehelper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@IgnoreExtraProperties
data class Word(
    val wordId: String? = null,
    val timesReviewed: Long? = null,
    val rating: Float? = null
) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}


class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        /** BOTTOM SHEET*/
        val modalBottomSheet = ModalBottomSheet()
        val bottomSheetButton = findViewById<Button>(R.id.bottomSheetExpandButton)
        bottomSheetButton.setOnClickListener {
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)

            //VIBRACION DE TECLA
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }

        /**PLAY BUTTON*/
        val playButton = findViewById<Button>(R.id.playBtn)
        playButton.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
            //VIBRACION DE TECLA
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }

        /**SEARCH BUTTON*/
        val searchButton = findViewById<Button>(R.id.searchBtn)
        searchButton.setOnClickListener {
            val intent = Intent(this, search::class.java)
            startActivity(intent)
            //VIBRACION DE TECLA
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }

        /**LEARN BUTTON*/
        val learnButton = findViewById<Button>(R.id.learnBtn)
        learnButton.setOnClickListener {
            val intent = Intent(this, Learn::class.java)
            startActivity(intent)
            //VIBRACION DE TECLA
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }


        /**RANKINGS BUTTON*/
        val rankingsButton = findViewById<Button>(R.id.rankingsBtn)
        rankingsButton.setOnClickListener {
            val text = "Rankings not implemented yet!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.setGravity(Gravity.CENTER, 0,300)
            toast.show()

            //VIBRACION DE TECLA
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
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

        /**PROFILE BUTTON*/
        val profileBtn = view.findViewById<Button>(R.id.profileBtn)
        profileBtn.setOnClickListener {

            //TODO SHOW TOAST - LEARN HOW TO GET CONTEXT

            val text = "Rankings not implemented yet!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(requireContext(), text, duration)
            //toast.setGravity(Gravity.CENTER, 0,0)
            toast.show()


        }

        /**SETTINGS BUTTON*/
        val settingsBtn = view.findViewById<Button>(R.id.settingsBtn)
        profileBtn.setOnClickListener {
        }

        /**CODE BUTTON*/
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

