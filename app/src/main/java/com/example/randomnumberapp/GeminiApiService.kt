// File: GeminiApiService.kt
package com.example.randomnumberapp

import retrofit2.http.GET
import retrofit2.http.Path

interface GeminiApiService {
    @GET("{number}")
    suspend fun getComment(@Path("number") number: Int): String
}
