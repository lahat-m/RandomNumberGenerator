package com.example.randomnumberapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomnumberapp.adapter.SavedFactsAdapter
import com.example.randomnumberapp.util.SharedPrefsHelper

class SavedFactsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SavedFactsAdapter
    private val savedFacts = mutableListOf<Pair<Int, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_facts)

        recyclerView = findViewById(R.id.saved_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        savedFacts.addAll(SharedPrefsHelper.getSavedFacts(this))
        adapter = SavedFactsAdapter(savedFacts) { number, comment ->
            SharedPrefsHelper.deleteFact(this, number, comment)
            savedFacts.remove(Pair(number, comment))
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter
    }
}
