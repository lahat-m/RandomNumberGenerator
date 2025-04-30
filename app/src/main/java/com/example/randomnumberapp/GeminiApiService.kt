package com.example.randomnumberapp

interface GeminiApiService {
    suspend fun getComment(number: Int): String
}
