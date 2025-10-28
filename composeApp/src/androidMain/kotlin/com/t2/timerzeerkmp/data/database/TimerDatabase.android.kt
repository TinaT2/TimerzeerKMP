package com.t2.timerzeerkmp.data.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.t2.timerzeerkmp.AppContextHolder

actual fun createTimerDatabase(): TimerDatabase {
    val context = AppContextHolder.context
    val dbFile = context.getDatabasePath("timer.db")
    return Room.databaseBuilder<TimerDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .build()
}
