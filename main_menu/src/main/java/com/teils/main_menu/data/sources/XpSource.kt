package com.teils.main_menu.data.sources

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class XpSource(private val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("xp_source_prefs", Context.MODE_PRIVATE)

    var vocabulary: Int
        get() = prefs.getInt("vocabulary", 0)
        set(value) = prefs.edit { putInt("vocabulary", value) }

    var grammar: Int
        get() = prefs.getInt("grammar", 0)
        set(value) = prefs.edit { putInt("grammar", value) }

    var communication: Int
        get() = prefs.getInt("communication", 0)
        set(value) = prefs.edit { putInt("communication", value) }
}