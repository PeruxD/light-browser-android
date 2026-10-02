package com.example.lightbrowser

import android.content.Context
import android.content.SharedPreferences

class BrowserPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("lightbrowser_prefs", Context.MODE_PRIVATE)

    fun saveExtensions(list: List<Extension>) {
        val set = list.map { it.toJson() }.toSet()
        prefs.edit().putStringSet("extensions", set).apply()
    }

    fun loadExtensions(): List<Extension> {
        val set = prefs.getStringSet("extensions", emptySet()) ?: emptySet()
        return set.mapNotNull { Extension.fromJson(it) }
    }
}