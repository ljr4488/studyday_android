package com.example.studyday_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.studyday_android.SharedPref.MyApplication

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogOut:Button
    lateinit var btnInfoChg:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogOut = findViewById<Button>(R.id.btnLogOut) as Button
        btnInfoChg = findViewById<Button>(R.id.btnInfoChg) as Button

        btnLogOut.setOnClickListener {
            MyApplication.prefs.setString("userId", "")
            MyApplication.prefs.setString("loginId", "")
            MyApplication.prefs.setString("name", "")

            finish()
        }

        btnInfoChg.setOnClickListener {
            startActivity(Intent(this@LoginActivity,InfochgActivity::class.java))
        }

    }
}