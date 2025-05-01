package com.example.randomnumberapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NumberRepository) : ViewModel() {

    private val _randomNumber = MutableLiveData<Int>()
    val randomNumber: LiveData<Int> = _randomNumber

    private val _comment = MutableLiveData<String>()
    val comment: LiveData<String> = _comment

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _history = MutableLiveData<List<Pair<Int, String>>>(emptyList())
    val history: LiveData<List<Pair<Int, String>>> = _history

    fun generateNewNumber() {
        _isLoading.value = true
        val newNumber = repository.generateRandomNumber()
        _randomNumber.value = newNumber

        viewModelScope.launch {
            try {
                val generatedComment = repository.getCommentForNumber(newNumber)
                _comment.value = generatedComment
                addToHistory(newNumber, generatedComment)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun addToHistory(number: Int, comment: String) {
        val currentHistory = _history.value ?: emptyList()
        val newHistory = currentHistory.toMutableList()
        newHistory.add(0, Pair(number, comment))
        if (newHistory.size > 10) {
            newHistory.removeAt(newHistory.size - 1)
        }
        _history.value = newHistory
    }

    fun setInitialState() {
        _comment.value = "Let's see what number you get..."
        _isLoading.value = false
    }
    fun generateNewNumberInRange(min: Int, max: Int) {
        _isLoading.value = true
        val newNumber = (min..max).random()
        _randomNumber.value = newNumber

        viewModelScope.launch {
            try {
                val generatedComment = repository.getCommentForNumber(newNumber)
                _comment.value = generatedComment
                addToHistory(newNumber, generatedComment)
            } finally {
                _isLoading.value = false
            }
        }
    }


}
