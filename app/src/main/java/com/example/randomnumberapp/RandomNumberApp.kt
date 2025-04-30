package com.example.randomnumberapp
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import android.app.Application



class RandomNumberApp : Application() {

    companion object {
        private lateinit var instance: RandomNumberApp
        private lateinit var geminiApiService: GeminiApiService

        fun getGeminiApiService(): GeminiApiService = geminiApiService
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val retrofit = Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create()) // plain text
            .build()

        geminiApiService = retrofit.create(GeminiApiService::class.java)
    }
}
