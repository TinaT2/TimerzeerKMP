package com.t2.timerzeerkmp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.t2.timerzeerkmp.data.persistence.DataStoreFields
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import org.jetbrains.compose.resources.StringResource

class SettingsRepository(private val dataStore: TimerPersistence) {

//todo    val settingsFlow = dataStore.data

    suspend fun saveEndingAnimation(nameId: StringResource) {
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey(DataStoreFields.ENDING_ANIMATION.name)] =
//                nameId.toString()
//        }
    }

    suspend fun saveBackgroundTheme(nameId: StringResource) {
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey(DataStoreFields.BACKGROUND.name)] =
//                nameId.toString()
//        }
    }

    suspend fun saveFontStyle(nameId: StringResource) {
//        dataStore.edit { preferences ->
//            preferences[stringPreferencesKey(DataStoreFields.FONT_STYLE.name)] = nameId.toString()
//        }
    }
}