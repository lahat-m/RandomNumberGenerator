package com.example.randomnumberapp.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPrefsHelper {
    private const val PREFS_NAME = "random_number_prefs"
    private const val SAVED_FACTS_KEY = "saved_facts"

    fun saveFact(context: Context, number: Int, comment: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val facts = getSavedFacts(context).toMutableList()
        if (!facts.contains(Pair(number, comment))) {
            facts.add(0, Pair(number, comment))
        }
        val json = Gson().toJson(facts)
        prefs.edit().putString(SAVED_FACTS_KEY, json).apply()
    }

    fun getSavedFacts(context: Context): List<Pair<Int, String>> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(SAVED_FACTS_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Pair<Int, String>>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun deleteFact(context: Context, number: Int, comment: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val facts = getSavedFacts(context).toMutableList()
        facts.remove(Pair(number, comment))
        val json = Gson().toJson(facts)
        prefs.edit().putString(SAVED_FACTS_KEY, json).apply()
    }
}
