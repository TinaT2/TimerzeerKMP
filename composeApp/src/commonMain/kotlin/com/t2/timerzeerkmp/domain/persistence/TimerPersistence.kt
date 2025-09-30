package com.t2.timerzeerkmp.domain.persistence

interface TimerPersistence {
    suspend fun saveStartEpochMillis(ms: Long)
    suspend fun saveInitialMilliSeconds(seconds: Long)
    suspend fun saveIsRunning(isRunning: Boolean)

    suspend fun clear()

    suspend fun getStartEpochMillis(): Long?
    suspend fun getInitialMilliSeconds(): Long?
    suspend fun getIsRunning(): Boolean?
}