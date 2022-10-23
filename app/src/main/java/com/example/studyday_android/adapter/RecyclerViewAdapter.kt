package com.example.studyday_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyday_android.R
import com.example.studyday_android.RecyclerViewItem

class RecyclerViewAdapter(private var recyclerViewItems: ArrayList<RecyclerViewItem>,
                          val itemClick: (RecyclerViewItem) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
//        (R.layout.item_recycler, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(recyclerViewItems[position])
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val subtitle: TextView = itemView.findViewById(R.id.subtitle)

        fun bind(recyclerViewItem: RecyclerViewItem) {
            title.text = recyclerViewItem.title
            subtitle.text = recyclerViewItem.subtitle
            itemView.setOnClickListener { itemClick(recyclerViewItem) }
        }

    }

}