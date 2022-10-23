package com.example.studyday_android

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import com.example.studyday_android.SharedPref.MyApplication
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var btnLogOut:Button
    lateinit var btnInfoChg:Button
    lateinit var dateBtn:Button
    lateinit var timeBtn:Button
    lateinit var reserveBtn:Button
    var dateString = ""
    var timeString = ""
    lateinit var numPick:NumberPicker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogOut = findViewById<Button>(R.id.btnLogOut) as Button
        btnInfoChg = findViewById<Button>(R.id.btnInfoChg) as Button
        dateBtn = findViewById<Button>(R.id.dateBtn) as Button
        timeBtn = findViewById<Button>(R.id.timeBtn) as Button
        reserveBtn = findViewById<Button>(R.id.reserveBtn) as Button

        dateBtn.setOnClickListener {
            val cal = Calendar.getInstance() // 캘린더 뷰 생성
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        var selectNum = 0
        timeBtn.setOnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_num_select, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.show()

            layout.findViewById<NumberPicker>(R.id.number_picker).minValue=9
            layout.findViewById<NumberPicker>(R.id.number_picker).maxValue=21

            if(selectNum!=0) layout.findViewById<NumberPicker>(R.id.number_picker).value = selectNum

            layout.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
                dialog.dismiss()
            }
            layout.findViewById<Button>(R.id.btn_ok).setOnClickListener {
                selectNum = layout.findViewById<NumberPicker>(R.id.number_picker).value
                Log.d("selectNum", "${selectNum}")
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

        reserveBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity,ReserveActivity::class.java))
        }


    }
}

