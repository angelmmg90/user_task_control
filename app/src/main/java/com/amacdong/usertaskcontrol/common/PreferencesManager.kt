package com.amacdong.usertaskcontrol.common

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager {
    enum class Status {
        ERROR, SUCCESS
    }

    companion object {
        private const val PREFERENCES_NAME: String = "UTC"
        private const val DEFAULT_ERROR_PREFERENCES_FLOAT: Float = -1f
        private const val DEFAULT_ERROR_PREFERENCES_INT: Int = -1
        private const val DEFAULT_ERROR_PREFERENCES_STRING: String = ""
        private const val DEFAULT_ERROR_PREFERENCES_BOOLEAN: Boolean = false

        private lateinit var prefs: SharedPreferences
        private lateinit var edit: SharedPreferences.Editor

        fun save(context: Context?, key: String?, value: Any?): Status {
            val validData: Boolean = !key.isNullOrBlank()
                    && value != null
                    && context != null

            if (validData) {
                if (saveByValue(context!!, key!!, value!!)) {
                    return Status.SUCCESS
                }
            }

            return Status.ERROR
        }

        private fun saveByValue(context: Context, key: String, value: Any): Boolean {
            prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            edit = prefs.edit()

            when (value) {
                is Int -> edit.putInt(key, value)
                is Boolean -> edit.putBoolean(key, value)
                is String -> edit.putString(key, value)
            }

            return edit.commit()
        }

        private fun restore(context: Context, key: String?): Status {
            prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)


            if (prefs.contains(key)) {
                return Status.SUCCESS
            }
            return Status.ERROR
        }

        private fun restoreInt(context: Context, key: String?): Int? = when (restore(context, key)) {
            Status.SUCCESS -> {
                prefs.getInt(key, DEFAULT_ERROR_PREFERENCES_INT)
            }
            Status.ERROR -> {
                null
            }
        }

        private fun restoreFloat(context: Context, key: String?): Float? = when (restore(context, key)) {
            Status.SUCCESS -> {
                prefs.getFloat(key, this.DEFAULT_ERROR_PREFERENCES_FLOAT)
            }
            Status.ERROR -> {
                null
            }
        }

         fun restoreBoolean(context: Context, key: String?): Boolean? = when (restore(context, key)) {
            Status.SUCCESS -> {
                prefs.getBoolean(key, DEFAULT_ERROR_PREFERENCES_BOOLEAN)
            }
            Status.ERROR -> {
                null
            }
        }

        fun restoreString(context: Context, key: String?): String? = when (restore(context, key)) {
            Status.SUCCESS -> {
                prefs.getString(key, DEFAULT_ERROR_PREFERENCES_STRING)
            }
            Status.ERROR -> {
                ""
            }
        }

        fun deleteKeyFromPreferences(context: Context, key: String): Boolean = try {
            prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            val edit = prefs.edit()
            edit.remove(key)
            edit.apply()
            true
        } catch (e: Exception) {
            false
        }
    }
}

