package com.t2.timerzeerkmp.data.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence

object TimerPrefsKeys {
    val START = longPreferencesKey("timer_start_epoch")
    val INITIAL = longPreferencesKey("timer_initial_seconds")
    val RUNNING = booleanPreferencesKey("timer_is_running")
}

class DataStoreTimerPersistence(
    private val dataStore: DataStore<Preferences>
) : TimerPersistence {

    override suspend fun saveStartEpochMillis(ms: Long) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.START] = ms }
    }

    override suspend fun saveInitialSeconds(seconds: Long) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.INITIAL] = seconds }
    }

    override suspend fun saveIsRunning(isRunning: Boolean) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.RUNNING] = isRunning }
    }

    override suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    override suspend fun getStartEpochMillis(): Long? =
        dataStore.data.first()[TimerPrefsKeys.START]

    override suspend fun getInitialSeconds(): Long? =
        dataStore.data.first()[TimerPrefsKeys.INITIAL]

    override suspend fun getIsRunning(): Boolean? =
        dataStore.data.first()[TimerPrefsKeys.RUNNING]
}