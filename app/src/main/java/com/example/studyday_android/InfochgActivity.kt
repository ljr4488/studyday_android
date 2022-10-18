package com.example.studyday_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.studyday_android.SharedPref.MyApplication

class InfochgActivity : AppCompatActivity() {
    lateinit var chgId:EditText
    lateinit var chgPw:EditText
    lateinit var chgPwChk:EditText
    lateinit var chgName:EditText
    lateinit var chgAddress:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infochg)

        chgId = findViewById(R.id.chgId)

        val loginId = MyApplication.prefs.getString("loginId", "")

        Log.e("userId", "${loginId}")
    }
}