package com.t2.timerzeerkmp.data.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.t2.timerzeerkmp.domain.persistence.TimerPersistence
import com.t2.timerzeerkmp.domain.timer.TimerMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TimerPrefsKeys {
    val START = longPreferencesKey("timer_start_epoch")
    val INITIAL = longPreferencesKey("timer_initial_seconds")
    val RUNNING = booleanPreferencesKey("timer_is_running")
    val TITLE = stringPreferencesKey("timer_title")
    val MODE = stringPreferencesKey("timer_mode")

    val ELAPSED_TIME = longPreferencesKey("timer_elapsed_time")
}

class DataStoreTimerPersistence(
    private val dataStore: DataStore<Preferences>
) : TimerPersistence {

    override suspend fun saveStartEpochMillis(ms: Long) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.START] = ms }
    }

    override suspend fun saveInitialMilliSeconds(seconds: Long) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.INITIAL] = seconds }
    }

    override suspend fun saveIsRunning(isRunning: Boolean) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.RUNNING] = isRunning }
    }

    override suspend fun saveTitle(title: String) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.TITLE] = title }
    }

    override suspend fun saveMode(mode: String) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.MODE] = mode }
    }

    override suspend fun saveElapsedTime(elapsedTime: Long) {
        dataStore.edit { prefs -> prefs[TimerPrefsKeys.ELAPSED_TIME] = elapsedTime }
    }

    override suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    override suspend fun getStartEpochMillis(): Flow<Long?> =
        dataStore.data.map { it[TimerPrefsKeys.START] }

    override suspend fun getInitialMilliSeconds(): Flow<Long?> =
        dataStore.data.map { it[TimerPrefsKeys.INITIAL] }

    override suspend fun getIsRunning(): Flow<Boolean?> =
        dataStore.data.map { it[TimerPrefsKeys.RUNNING] }

    override suspend fun getTitle(): Flow<String> =
        dataStore.data.map { it[TimerPrefsKeys.TITLE]?:"" }


    override suspend fun getMode(): Flow<String> =
        dataStore.data.map { it[TimerPrefsKeys.MODE] ?: TimerMode.STOPWATCH.name }

    override suspend fun getElapsedTime(): Flow<Long?> =
            dataStore.data.map { it[TimerPrefsKeys.ELAPSED_TIME]  }

}