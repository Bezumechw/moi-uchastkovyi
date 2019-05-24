package com.example.moiuchastkoviy.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.moiuchastkoviy.ui.main.MainActivity
import com.example.moiuchastkoviy.R
//Заставка

class SplashScreenActivity : AppCompatActivity() {
    val SPLASH_DISPLAY_LENGTH = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({

            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }


}
