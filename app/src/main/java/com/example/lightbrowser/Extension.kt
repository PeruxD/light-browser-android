package com.example.lightbrowser

import org.json.JSONObject

data class Extension(
    val id: String,
    val name: String,
    val script: String,
    val enabled: Boolean = true
) {
    fun toJson(): String {
        return JSONObject().apply {
            put("id", id)
            put("name", name)
            put("script", script)
            put("enabled", enabled)
        }.toString()
    }

    companion object {
        fun fromJson(json: String): Extension? {
            return try {
                val obj = JSONObject(json)
                Extension(
                    id = obj.getString("id"),
                    name = obj.getString("name"),
                    script = obj.optString("script", ""),
                    enabled = obj.optBoolean("enabled", true)
                )
            } catch (_: Exception) {
                null
            }
        }
    }
}