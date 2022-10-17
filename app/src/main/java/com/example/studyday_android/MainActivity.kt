package com.example.studyday_android

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.studyday_android.SharedPref.MyApplication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp) as Button

        val loginId = MyApplication.prefs.getString("loginId", "")
        val password = MyApplication.prefs.getString("password", "")

        if(loginId == "" && password == "") {
            val btnLogin = findViewById<Button>(R.id.btnLogin) as Button
            btnLogin.visibility = View.GONE
            btnLogin.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}