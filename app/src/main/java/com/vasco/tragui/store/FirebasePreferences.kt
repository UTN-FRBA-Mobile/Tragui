package com.vasco.tragui.store

import android.content.Context
import android.content.SharedPreferences

object FirebasePreferences  {
    private val PREF_NAME = "PREF_NAME"
    private val FIREBASE_TOKEN = "FIREBASE_TOKEN"

    fun getFirebaseToken(context: Context): String? {
        return getPreferences(context).getString(FIREBASE_TOKEN, null)
    }

    fun setFirebaseToken(context: Context, token: String) {
        val editor = getPreferencesEditor(context)
        editor.putString(FIREBASE_TOKEN, token)
        editor.apply()
    }

    private fun getPreferencesEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}