package com.example.randomnumberapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.randomnumberapp.R

class SavedFactsAdapter(
    private val facts: List<Pair<Int, String>>,
    private val onDelete: (Int, String) -> Unit
) : RecyclerView.Adapter<SavedFactsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById(R.id.saved_number)
        val comment: TextView = itemView.findViewById(R.id.saved_comment)
        val deleteBtn: Button = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_fact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (num, text) = facts[position]
        holder.number.text = num.toString()
        holder.comment.text = text
        holder.deleteBtn.setOnClickListener {
            onDelete(num, text)
        }
    }

    override fun getItemCount() = facts.size
}
