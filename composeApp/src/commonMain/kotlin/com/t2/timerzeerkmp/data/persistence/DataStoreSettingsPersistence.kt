package com.t2.timerzeerkmp.data.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.t2.timerzeerkmp.domain.persistence.SettingsPersistence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.StringResource


enum class SettingsPrefsKeys(val value: Preferences.Key<String>) {
    ENDING_ANIMATION(stringPreferencesKey("ending_animation")),
     BACKGROUND(stringPreferencesKey("background")),
     FONT_STYLE (stringPreferencesKey("font_style"))
}

class DataStoreSettingsPersistence(val dataStore: DataStore<Preferences>) : SettingsPersistence {
    override suspend fun saveEndingAnimation(nameId: StringResource) {
        dataStore.edit { preferences ->
            preferences[SettingsPrefsKeys.ENDING_ANIMATION.value] = nameId.key
        }
    }

    override suspend fun saveBackgroundTheme(nameId: StringResource) {
        dataStore.edit { preferences ->
            preferences[SettingsPrefsKeys.BACKGROUND.value] = nameId.key
        }
    }

    override suspend fun saveFontStyle(nameId: StringResource) {
        dataStore.edit { preferences ->
            preferences[SettingsPrefsKeys.FONT_STYLE.value] = nameId.key
        }
    }

    override suspend fun getEndingAnimation(): Flow<String?> {
        return dataStore.data.map { it[SettingsPrefsKeys.ENDING_ANIMATION.value] }
    }

    override suspend fun getBackgroundTheme(): Flow<String?> {
        return dataStore.data.map { it[SettingsPrefsKeys.BACKGROUND.value] }
    }

    override suspend fun getFontStyleKeResource(): Flow<String?> {
        return dataStore.data.map { it[SettingsPrefsKeys.FONT_STYLE.value] }
    }
}