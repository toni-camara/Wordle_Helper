@file:Suppress("DEPRECATION")

package android.example.wordlehelper

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

class MyMethods {

    fun vibratePhone(context: Context) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(
            VibrationEffect.createOneShot(
                50,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

}