package com.example.randomnumberapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomnumberapp.adapter.HistoryAdapter
import com.example.randomnumberapp.util.SharedPrefsHelper

class MainActivity : AppCompatActivity() {
    private lateinit var numberTextView: TextView
    private lateinit var commentTextView: TextView
    private lateinit var generateButton: Button
    private lateinit var loadingIndicator: TextView
    private lateinit var numberCard: CardView
    private lateinit var commentCard: CardView
    private lateinit var recyclerView: RecyclerView
    private lateinit var saveButton: Button
    private lateinit var viewSavedButton: Button
    private lateinit var minInput: EditText
    private lateinit var maxInput: EditText

    private val historyList = mutableListOf<Pair<String, String>>()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var viewModel: MainViewModel
    private var pendingNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val geminiApiService = RandomNumberApp.getGeminiApiService()
        val factory = ViewModelFactory(geminiApiService)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        initializeViews()
        setupRecyclerView()
        setupObservers()

        numberTextView.text = "Press the button!"
        viewModel.setInitialState()

        generateButton.setOnClickListener {
            val min = minInput.text.toString().toIntOrNull() ?: 1
            val max = maxInput.text.toString().toIntOrNull() ?: 100
            if (min >= max) {
                Toast.makeText(this, "Min must be less than Max", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.generateNewNumberInRange(min, max)
            }
        }

        saveButton.setOnClickListener {
            val number = numberTextView.text.toString().toIntOrNull()
            val comment = commentTextView.text.toString()
            if (number != null && comment.isNotBlank()) {
                SharedPrefsHelper.saveFact(this, number, comment)
                Toast.makeText(this, "Fact saved!", Toast.LENGTH_SHORT).show()
            }
        }

        viewSavedButton.setOnClickListener {
            startActivity(Intent(this, SavedFactsActivity::class.java))
        }
    }

    private fun initializeViews() {
        numberTextView = findViewById(R.id.number_text_view)
        commentTextView = findViewById(R.id.comment_text_view)
        generateButton = findViewById(R.id.generate_button)
        loadingIndicator = findViewById(R.id.loading_indicator)
        numberCard = findViewById(R.id.number_card)
        commentCard = findViewById(R.id.comment_card)
        recyclerView = findViewById(R.id.history_recycler_view)
        saveButton = findViewById(R.id.save_button)
        viewSavedButton = findViewById(R.id.view_saved_button)
        minInput = findViewById(R.id.min_input)
        maxInput = findViewById(R.id.max_input)
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(historyList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = historyAdapter
    }

    private fun setupObservers() {
        viewModel.randomNumber.observe(this) { number ->
            pendingNumber = number.toString()
            numberTextView.text = pendingNumber
            numberCard.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))
        }

        viewModel.comment.observe(this) { comment ->
            commentTextView.text = comment
            commentCard.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))

            historyList.add(0, Pair(pendingNumber, comment))
            historyAdapter.notifyItemInserted(0)
            recyclerView.scrollToPosition(0)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            generateButton.isEnabled = !isLoading
            loadingIndicator.text = if (isLoading) "Loading..." else ""
        }
    }
}
