package com.example.TestTwo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.TestTwo.R

class SmsAdapter(val items : ArrayList<SmsData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of Messages in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items.get(position).message
        holder.date.text = items.get(position).date
        holder.sender.text = items.get(position).senderName
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val sender: TextView = itemView.findViewById(R.id.sms_sender)
    val textView: TextView = itemView.findViewById(R.id.sms_message)
    val date: TextView = itemView.findViewById(R.id.sms_date)
}