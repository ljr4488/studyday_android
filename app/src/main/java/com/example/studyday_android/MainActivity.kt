package com.example.studyday_android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studyday_android.SharedPref.MyApplication
import com.example.studyday_android.apis.APIS
import com.example.studyday_android.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var id:EditText
    lateinit var pw:EditText
    lateinit var button:Button
    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignUp = findViewById<Button>(R.id.btnInfoChg) as Button


        // session 처리와 같은 구현을 위함
        val userId = MyApplication.prefs.getString("userId", "")
        val loginId = MyApplication.prefs.getString("loginId", "")
        val name = MyApplication.prefs.getString("name", "")

        if(userId != "") {
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }
        id = findViewById(R.id.LoginId)
        pw = findViewById(R.id.Password)
        button = findViewById(R.id.btnLogOut)

        button.setOnClickListener {
            //다이얼로그 Alert 생성용
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            //계정 패스워드 String
            val idStr = id.text.toString()
            var pwStr = pw.text.toString()
            api.login(idStr).enqueue(object :Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful) {
                        val result: User? = response.body()
                        val chkloginId : String = result!!.data!!.get("login_id").toString().replace("\"", "")
                        val chkloginPw : String = result!!.data!!.get("password").toString().replace("\"", "")
                        val chkloginName : String = result!!.data!!.get("name").toString().replace("\"", "")
                        val chkUserId : String = result!!.data!!.get("user_id").toString().replace("\"", "")
                        if(idStr == chkloginId && pwStr == chkloginPw) {
                            MyApplication.prefs.setString("userId", "${chkUserId}")
                            MyApplication.prefs.setString("loginId", "${chkloginId}")
                            MyApplication.prefs.setString("name", "${chkloginName}")
                            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        } else if(idStr == "") {
                            dlg.setTitle("로그인 에러")
                            dlg.setMessage("아이디를 입력하세요.")
                            dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            dlg.show()
                        } else if(idStr != "" && pwStr == "") {
                            dlg.setTitle("로그인 에러")
                            dlg.setMessage("패스워드를 입력하세요.")
                            dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            dlg.show()
                        } else if(idStr == chkloginId && pwStr != chkloginPw) {
                            dlg.setTitle("로그인 에러")
                            dlg.setMessage("패스워드가 일치하지 않습니다.")
                            dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                            })
                            dlg.show()
                        }
                    } else {
                        dlg.setTitle("로그인 에러")
                        dlg.setMessage("계정정보를 입력하세요.")
                        dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        })
                        dlg.show()
                    }

                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    dlg.setTitle("로그인 에러")
                    dlg.setMessage("통신이 원할하지 않습니다.")
                    dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    })
                    dlg.show()
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