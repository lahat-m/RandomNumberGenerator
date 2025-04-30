package com.example.randomnumberapp
import android.util.Log

class NumberRepository(private val geminiApiService: GeminiApiService) {
    fun generateRandomNumber(): Int {
        return (1..100).random()
    }

    suspend fun getCommentForNumber(number: Int): String {
        return try {
            geminiApiService.getComment(number)
        } catch (e: Exception) {
            Log.e("NumberRepository", "API call failed: ${e.localizedMessage}", e)
            "No fact available. Try again!"
        }
    }


}
