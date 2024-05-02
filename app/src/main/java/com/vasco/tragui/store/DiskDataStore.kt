package com.vasco.tragui.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DiskDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val USER_SELECTED_BOTTLES = stringPreferencesKey("selected_bottles")
    }

    fun getSelectedBottles(): Flow<String?> {
      val selectedBottles = context.dataStore.data
          .map { preferences ->
              preferences[USER_SELECTED_BOTTLES] ?: ""
          }
        return selectedBottles
    }

    suspend fun saveBottles(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_SELECTED_BOTTLES] = name
        }
    }
}