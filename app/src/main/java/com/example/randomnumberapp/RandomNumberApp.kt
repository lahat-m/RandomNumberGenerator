package com.example.randomnumberapp

import android.app.Application

class RandomNumberApp : Application() {

    companion object {
        private lateinit var instance: RandomNumberApp

        private val geminiApiService = object : GeminiApiService {
            override suspend fun getComment(number: Int): String {
                return "You got number $number — that's awesome!"
            }
        }

        fun getGeminiApiService(): GeminiApiService = geminiApiService
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
