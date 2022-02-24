package com.example.foody.ui.ActivitiesPackage.Welcome

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.foody.R
import com.example.foody.ui.ActivitiesPackage.Login.LoginActivity
import com.example.foody.ui.ActivitiesPackage.Register.RegisterActivity
import com.example.foody.ui.MainActivity



class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.WelcomeActivityStyle)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.hide()



        val welcome_login_btn = findViewById<Button>(R.id.login_button_welcome)
        val welcome_sign_up_btn = findViewById<Button>(R.id.signup_button_welcome)
        val welcome_signing_up = findViewById<TextView>(R.id.welcome_signing_up)

        welcome_login_btn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        welcome_sign_up_btn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        welcome_signing_up.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}