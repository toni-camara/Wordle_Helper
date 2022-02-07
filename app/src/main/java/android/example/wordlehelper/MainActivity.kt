package android.example.wordlehelper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


        /**STATS BUTTON*/
        val statsButton = findViewById<Button>(R.id.statsBtn)
        statsButton.setOnClickListener {
            val intent = Intent(this, Stats::class.java)
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

        /**CODE BUTTON*/
        val codeButton = findViewById<Button>(R.id.codeBtn)
        codeButton.setOnClickListener {
            val intent = Intent(this, Code::class.java)
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

    }
}

