package com.example.studyday_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyday_android.adapter.RecyclerViewAdapter

class ReserveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)

        //리스트에 사용될 ArrayList를 생성한다.
        val recyclerViewItems: ArrayList<RecyclerViewItem> = ArrayList()
        recyclerViewItems.add(RecyclerViewItem(1, "title1", "subtitle1"))
        recyclerViewItems.add(RecyclerViewItem(2, "title2", "subtitle2"))
        recyclerViewItems.add(RecyclerViewItem(3, "title3", "subtitle3"))
        recyclerViewItems.add(RecyclerViewItem(4, "title4", "subtitle4"))
        recyclerViewItems.add(RecyclerViewItem(5, "title5", "subtitle5"))

        //recyclerView 연결
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(recyclerViewItems) { recyclerViewItem ->
            Toast.makeText(this, "${recyclerViewItem.title} 클릭", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter
    }
}

//리스트에 사용될 객체
data class RecyclerViewItem(
    var id: Int = 0,
    var title: String = "",
    var subtitle: String = ""
)