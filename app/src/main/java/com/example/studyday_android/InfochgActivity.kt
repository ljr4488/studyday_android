package com.example.studyday_android

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.studyday_android.SharedPref.MyApplication
import com.example.studyday_android.apis.APIS
import com.example.studyday_android.data.InfoChg
import com.example.studyday_android.data.InfoChgData
import com.example.studyday_android.data.PostResult
import com.example.studyday_android.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfochgActivity : AppCompatActivity() {
    lateinit var chgId:EditText
    lateinit var chgPw:EditText
    lateinit var chgPwChk:EditText
    lateinit var chgName:EditText
    lateinit var chgAddress:EditText
    lateinit var chgInfoBtn:Button
    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infochg)

        chgId = findViewById(R.id.chgId)
        chgPw = findViewById(R.id.chgPw)
        chgPwChk = findViewById(R.id.chgPwChk)
        chgName = findViewById(R.id.chgName)
        chgAddress = findViewById(R.id.chgAddress)
        chgInfoBtn = findViewById(R.id.chgInfoBtn)

        val userId = MyApplication.prefs.getString("userId", "")
        val loginId = MyApplication.prefs.getString("loginId", "")
        val dlg: AlertDialog.Builder = AlertDialog.Builder(this@InfochgActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        api.login(loginId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful) {
                    val result: User? = response.body()
                    val loginId : String = result!!.data!!.get("login_id").toString().replace("\"", "")
                    val loginPw : String = result!!.data!!.get("password").toString().replace("\"", "")
                    val name : String = result!!.data!!.get("name").toString().replace("\"", "")
                    val address : String = result!!.data!!.get("address").toString().replace("\"", "")

                    chgId.setText(loginId)
                    chgPw.setText(loginPw)
                    chgPwChk.setText(loginPw)
                    chgName.setText(name)
                    chgAddress.setText(address)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                dlg.setTitle("?????????????????? ??????")
                dlg.setMessage("????????? ???????????? ????????????.")
                dlg.setPositiveButton("??????", DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
                dlg.show()
                Log.e("?????????????????? onFailure", "${t.localizedMessage}")
            }
        })

        chgInfoBtn.setOnClickListener {
            val chgUserId: Int = userId.toInt()
            val chgLoginId: String = chgId.text.toString()
            val chgPassword: String = chgPw.text.toString()
            val chgUserName: String = chgName.text.toString()
            val chgUserAddress: String = chgAddress.text.toString()

            Log.d("???????????? ?????????: ", "userId : ${chgUserId} chgLoginId : ${chgLoginId} chgPassword : ${chgPassword} chgUserName : ${chgUserName} chgUserAddress : ${chgUserAddress}" )

            val infoChgData = InfoChgData(chgUserId, chgLoginId, chgPassword, chgUserName, chgUserAddress)
            val data = InfoChg(infoChgData, "OK")
            Log.d("data : ", "${data}")

            api.userInfoChg(data).enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    MyApplication.prefs.setString("loginId", "${chgLoginId}")
                    MyApplication.prefs.setString("name", "${chgUserName}")

                    finish()
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d("PUT ??????", ",")
                }

            })
        }
    }
}


//val chgUserId: Int = userId.toInt()
//val chgLoginId: String = chgId.text.toString()
//val chgPassword: String = chgPw.text.toString()
//val chgUserName: String = chgName.text.toString()
//val chgUserAddress: String = chgAddress.text.toString()
//
