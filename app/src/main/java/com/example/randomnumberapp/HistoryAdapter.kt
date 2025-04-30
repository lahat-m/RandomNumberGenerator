package com.example.randomnumberapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomnumberapp.R

class HistoryAdapter(
    private val historyList: List<Pair<String, String>>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberText: TextView = itemView.findViewById(R.id.history_number)
        val commentText: TextView = itemView.findViewById(R.id.history_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val (number, comment) = historyList[position]
        holder.numberText.text = number
        holder.commentText.text = comment
    }

    override fun getItemCount(): Int = historyList.size
}
