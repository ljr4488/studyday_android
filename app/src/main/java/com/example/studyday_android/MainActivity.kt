package com.example.studyday_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studyday_android.SharedPref.MyApplication
import com.example.studyday_android.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {
    lateinit var id:EditText
    lateinit var pw:EditText
    lateinit var button:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp) as Button

        val loginId = MyApplication.prefs.getString("loginId", "")
        val password = MyApplication.prefs.getString("password", "")

//        if(loginId == "" && password == "") {
//            val btnLogin = findViewById<Button>(R.id.btnLogin) as Button
//            btnLogin.visibility = View.GONE
//            btnLogin.setOnClickListener {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//            }
//        }

        id = findViewById(R.id.LoginId)
        pw = findViewById(R.id.Password)
        button = findViewById(R.id.btnLogin)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://220.86.211.113:8080/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(SignService::class.java)

        button.setOnClickListener {
            val idStr = id.text.toString()
            Log.e("idStr ", "${idStr}")
            service.login().enqueue(object :Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful) {
                        val result: User? = response.body()
                        Log.e("로그인 onResponse", "${result!!.data}")

                    } else {
                        Log.e("로그인 onResponse Failed", "실패")
                    }

                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("로그인 onFailure", "${t.localizedMessage}")
                }
            })
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}

interface SignService {

//    @FormUrlEncoded
    @GET("user/ljr4488")
    fun login() : Call<User>
}