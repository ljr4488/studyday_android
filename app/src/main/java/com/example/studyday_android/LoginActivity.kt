package com.example.studyday_android

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import com.example.studyday_android.SharedPref.MyApplication
import com.example.studyday_android.apis.APIS
import com.example.studyday_android.data.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var btnLogOut:Button
    lateinit var btnInfoChg:Button
    lateinit var dateBtn:Button
    lateinit var timeBtn:Button
    lateinit var searchBtn:Button
    var dateString = ""
    var timeInfo:Int = 0
    lateinit var numPick:NumberPicker

    //Room Button
    lateinit var firstRoomBtn:Button
    lateinit var secondRoomBtn:Button
    lateinit var thirdRoomBtn:Button
    lateinit var fourthRoomBtn:Button
    lateinit var fifthRoomBtn:Button

    //Room Txt
    lateinit var firstRoomTxt:TextView
    lateinit var secondRoomTxt:TextView
    lateinit var thirdRoomTxt:TextView
    lateinit var fourthRoomTxt:TextView
    lateinit var fifthRoomTxt:TextView

    //userId
    val userId = Integer.parseInt(MyApplication.prefs.getString("userId", ""))
    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogOut = findViewById<Button>(R.id.btnLogOut) as Button
        btnInfoChg = findViewById<Button>(R.id.btnInfoChg) as Button
        dateBtn = findViewById<Button>(R.id.dateBtn) as Button
        timeBtn = findViewById<Button>(R.id.timeBtn) as Button
        searchBtn = findViewById<Button>(R.id.searchBtn) as Button

        //Room Button
        firstRoomBtn = findViewById<Button>(R.id.firstRoomBtn) as Button
        secondRoomBtn = findViewById<Button>(R.id.secondRoomBtn) as Button
        thirdRoomBtn = findViewById<Button>(R.id.thirdRoomBtn) as Button
        fourthRoomBtn = findViewById<Button>(R.id.fourthRoomBtn) as Button
        fifthRoomBtn = findViewById<Button>(R.id.fifthRoomBtn) as Button

        //Room Txt
        firstRoomTxt = findViewById(R.id.firstRoomTxt)
        secondRoomTxt = findViewById(R.id.secondRoomTxt)
        thirdRoomTxt = findViewById(R.id.thirdRoomTxt)
        fourthRoomTxt = findViewById(R.id.fourthRoomTxt)
        fifthRoomTxt = findViewById(R.id.fifthRoomTxt)

        dateBtn.setOnClickListener {
            val cal = Calendar.getInstance() // 캘린더 뷰 생성
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}-${month+1}-${dayOfMonth}"
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        timeBtn.setOnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_num_select, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.show()

            layout.findViewById<NumberPicker>(R.id.number_picker).minValue=9
            layout.findViewById<NumberPicker>(R.id.number_picker).maxValue=21

            if(timeInfo!=0) layout.findViewById<NumberPicker>(R.id.number_picker).value = timeInfo

            layout.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                dialog.dismiss()
            }
            layout.findViewById<Button>(R.id.btn_ok).setOnClickListener {
                timeInfo = layout.findViewById<NumberPicker>(R.id.number_picker).value
                Log.d("selectNum", "${timeInfo}")
                dialog.dismiss()
            }
        }


        btnLogOut.setOnClickListener {
            MyApplication.prefs.setString("userId", "")
            MyApplication.prefs.setString("loginId", "")
            MyApplication.prefs.setString("name", "")

            finish()
        }

        btnInfoChg.setOnClickListener {
            startActivity(Intent(this@LoginActivity,InfochgActivity::class.java))
        }

        firstRoomBtn.visibility = View.INVISIBLE
        secondRoomBtn.visibility = View.INVISIBLE
        thirdRoomBtn.visibility = View.INVISIBLE
        fourthRoomBtn.visibility = View.INVISIBLE
        fifthRoomBtn.visibility = View.INVISIBLE

        firstRoomTxt.visibility = View.INVISIBLE
        secondRoomTxt.visibility = View.INVISIBLE
        thirdRoomTxt.visibility = View.INVISIBLE
        fourthRoomTxt.visibility = View.INVISIBLE
        fifthRoomTxt.visibility = View.INVISIBLE


        searchBtn.setOnClickListener {
            firstRoomBtn.setClickable(false)
            secondRoomBtn.setClickable(false)
            thirdRoomBtn.setClickable(false)
            fourthRoomBtn.setClickable(false)
            fifthRoomBtn.setClickable(false)
            Log.d("Test", "${dateString}, ${timeInfo}")
            if(dateString=="" && timeInfo==0) {
//                Log.d("dateString, timeInfo 여", "${dateString}, ${timeInfo}")
            } else {
//                Log.d("date, time", "${dateString}, ${timeInfo}")
//          계정 패스워드 String
                roomCheck(501)
                roomCheck(502)
                roomCheck(503)
                roomCheck(504)
                roomCheck(505)
            }
        }

        firstRoomBtn.setOnClickListener {
            reserveDialog(501)
        }

        secondRoomBtn.setOnClickListener {
            reserveDialog(502)
        }

        thirdRoomBtn.setOnClickListener {
            reserveDialog(503)
        }

        fourthRoomBtn.setOnClickListener {
            reserveDialog(504)
        }

        fifthRoomBtn.setOnClickListener {
            reserveDialog(505)
        }
    }

    fun roomCheck(roomId:Int) {
        System.out.println(dateString)
        System.out.println(timeInfo)
        api.roomCheck(roomId, dateString, timeInfo).enqueue(object : Callback<Reserve> {
            override fun onResponse(call: Call<Reserve>, response: Response<Reserve>) {
                val result: Reserve? = response.body()
                val description: String = result!!.description
                if(response.isSuccessful) {
                    val result: Reserve? = response.body()
                    Log.d(description, "${description}")
                    if(description!="OK") {
                        if(roomId == 501) {
                            firstRoomBtn.visibility = View.VISIBLE
                            firstRoomBtn.setClickable(true)
                            firstRoomTxt.visibility = View.VISIBLE
                            firstRoomTxt.text = "예약가능"
                        } else if(roomId == 502) {
                            secondRoomBtn.visibility = View.VISIBLE
                            secondRoomBtn.setClickable(true)
                            secondRoomTxt.visibility = View.VISIBLE
                            secondRoomTxt.text = "예약가능"
                        } else if(roomId == 503) {
                            thirdRoomBtn.visibility = View.VISIBLE
                            thirdRoomBtn.setClickable(true)
                            thirdRoomTxt.visibility = View.VISIBLE
                            thirdRoomTxt.text = "예약가능"
                        } else if(roomId == 504) {
                            fourthRoomBtn.visibility = View.VISIBLE
                            fourthRoomBtn.setClickable(true)
                            fourthRoomTxt.visibility = View.VISIBLE
                            fourthRoomTxt.text = "예약가능"
                        } else if(roomId == 505) {
                            fifthRoomBtn.visibility = View.VISIBLE
                            fifthRoomBtn.setClickable(true)
                            fifthRoomTxt.visibility = View.VISIBLE
                            fifthRoomTxt.text = "예약가능"

                        }
                    }
                }

            }
            override fun onFailure(call: Call<Reserve>, t: Throwable) {

            }
        })
    }

//    val joinData = JoinData("${address}", "${id}", "${name}", "${pw}")
//    val data = Join(joinData, "OK")
//    Log.d("userId 여", "${userId}")
//    Log.d("reserveDate 여", "${dateString}")
//    Log.d("startTime 여", "${timeInfo}")
//    Log.d("endTime 여", "${timeInfo+1}")
    fun roomReserve(RroomId:Int) {
        val reserveData = ReserveData("${RroomId}", "${userId}", "${dateString}", "${timeInfo}")
        val data = Reserve("OK", reserveData)
        api.reserveCreate(data).enqueue(object : Callback<PostResult> {
            override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                Log.d("data", "${data}")
                Log.d("Response:: ", response.body().toString())
            }

            override fun onFailure(call: Call<PostResult>, t: Throwable) {
                Log.d("CometChatAPI::", "Failed API call with call: " + call +
                        " + exception: ")
            }
        })
    }

    fun reserveDialog(roomId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("예약 확인")
            .setMessage("예약 하시겠습니까?")
            .setPositiveButton("확인", DialogInterface.OnClickListener{ dialog, id ->
                roomReserve(roomId)
                if(roomId == 501) {
                    firstRoomBtn.setClickable(false)
                    firstRoomTxt.text = "예약완료"
                } else if(roomId == 502) {
                    secondRoomBtn.setClickable(false)
                    secondRoomTxt.text = "예약완료"
//                    secondRoomBtn.visibility = View.INVISIBLE
                } else if(roomId == 503) {
                    thirdRoomBtn.setClickable(false)
                    thirdRoomTxt.text = "예약완료"
//                    thirdRoomBtn.visibility = View.INVISIBLE
                } else if(roomId == 504) {
                    fourthRoomBtn.setClickable(false)
                    fourthRoomTxt.text = "예약완료"
//                    fourthRoomBtn.visibility = View.INVISIBLE
                } else if(roomId == 505) {
                    fifthRoomBtn.setClickable(false)
                    fifthRoomTxt.text = "예약완료"
//                    fifthRoomBtn.visibility = View.INVISIBLE
                }
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->

            })
        builder.show()
    }
}

