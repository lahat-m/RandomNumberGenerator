package com.example.randomnumberapp

class NumberRepository(private val geminiApiService: GeminiApiService) {
    fun generateRandomNumber(): Int {
        return (1..100).random()
    }

    suspend fun getCommentForNumber(number: Int): String {
        return geminiApiService.getComment(number)
    }
}
