package com.t2.timerzeerkmp.domain.persistence

import kotlinx.coroutines.flow.Flow

interface TimerPersistence {
    suspend fun saveStartEpochMillis(ms: Long)
    suspend fun saveInitialMilliSeconds(seconds: Long)
    suspend fun saveIsRunning(isRunning: Boolean)
    suspend fun saveTitle(title: String)
    suspend fun saveMode(mode: String)
    suspend fun saveElapsedTime(elapsedTime: Long)

    suspend fun clear()

    suspend fun getStartEpochMillis(): Flow<Long?>
    suspend fun getInitialMilliSeconds(): Flow<Long?>
    suspend fun getIsRunning(): Flow<Boolean?>
    suspend fun getTitle(): Flow<String>
    suspend fun getMode(): Flow<String>
    suspend fun getElapsedTime(): Flow<Long?>
}