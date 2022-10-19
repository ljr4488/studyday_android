package com.example.studyday_android

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.studyday_android.apis.APIS
import com.example.studyday_android.data.Join
import com.example.studyday_android.data.JoinData
import com.example.studyday_android.data.PostResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {
    lateinit var id: EditText
    lateinit var pw: EditText
    lateinit var pw2: EditText
    lateinit var name: EditText
    lateinit var address: EditText
    lateinit var button: Button

    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        id = findViewById(R.id.signUpId)
        pw = findViewById(R.id.signUpPw)
        pw2 = findViewById(R.id.signUpPwChk)
        name = findViewById(R.id.signUpName)
        address = findViewById(R.id.signUpAddress)
        button = findViewById<Button>(R.id.signUp) as Button

        button.setOnClickListener {
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this@JoinActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            val joinAssignBtn = findViewById<Button>(R.id.signUp) as Button
            val id = id.text.toString()
            val pw = pw.text.toString()
            val pw2 = pw2.text.toString()
            val name = name.text.toString()
            val address = address.text.toString()
            if(id == "") {
                dlg.setTitle("회원가입 에러")
                dlg.setMessage("아이디를 입력하세요.")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            } else if(pw == "") {
                dlg.setTitle("회원가입 에러")
                dlg.setMessage("패스워드를 입력하세요.")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            } else if(pw2 == "") {
                dlg.setTitle("회원가입 에러")
                dlg.setMessage("패스워드 확인을 입력하세요.")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            } else if(name == "") {
                dlg.setTitle("회원가입 에러")
                dlg.setMessage("이름을 입력하세요.")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            } else if(address == "") {
                dlg.setTitle("회원가입 에러")
                dlg.setMessage("주소정보를 입력하세요.")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            } else if(pw != pw2) {
                dlg.setTitle("회원가입 에러")
                dlg.setMessage("패스워드와 패스워드 확인 결과가 일치하지 않습니다.")
                dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            } else {
                val joinData = JoinData("${address}", "${id}", "${name}", "${pw}")
                val data = Join(joinData, "OK")
                api.userCreate(data).enqueue(object : Callback<PostResult> {
                    override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                        Log.d("data", "${data}")
                        Log.d("Response:: ", response.body().toString())
                        finish()
                    }

                    override fun onFailure(call: Call<PostResult>, t: Throwable) {
                        Log.d("CometChatAPI::", "Failed API call with call: " + call +
                                " + exception: ")
                    }
                })
            }
        }
    }
}