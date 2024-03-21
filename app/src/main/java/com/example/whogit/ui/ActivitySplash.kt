package com.example.whogit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.whogit.R
import com.example.whogit.ui.main.MainActivity

class ActivitySplash : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME_OUT: Long = 2500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val ivRandom: ImageView = findViewById(R.id.ivRandom )

        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        ivRandom.startAnimation(fadeInAnimation)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}
