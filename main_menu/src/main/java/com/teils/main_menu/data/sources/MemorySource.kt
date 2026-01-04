package com.teils.main_menu.data.sources

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class MemorySource(private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("tellem_prefs", Context.MODE_PRIVATE)

    fun wasCoursePassed(id: Int): Boolean = prefs.contains("course $id")
    fun courseWasPassed(id: Int) = prefs.edit { putBoolean("course $id", true) }

    fun wasFlashcardsPassed(id: Int): Boolean = prefs.contains("flashcards $id")
    fun flashcardsWasPassed(id: Int) = prefs.edit { putBoolean("flashcards $id", true) }
}