package com.example.trialapp.ui

import android.content.Context

import android.content.SharedPreferences


class SessionManager(var _context: Context) {
    // Add other keys as needed
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.commit()
    }

    val isLoggedIn: Boolean
        get() = pref.getBoolean(KEY_IS_LOGGED_IN, false)
    var userName: String?
        get() = pref.getString(KEY_USER_NAME, null)
        set(userName) {
            editor.putString(KEY_USER_NAME, userName)
            editor.commit()
        }

    // Add other session management methods as needed
    fun logoutUser() {
        // Clear the preferences
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val PREF_NAME = "AppSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_NAME = "userName"
    }
}
