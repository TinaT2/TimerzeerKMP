package com.t2.timerzeerkmp.data.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

actual fun createTimerDatabase(): TimerDatabase {
    val dbFile = NSHomeDirectory() + "/timer.db"
    return Room.databaseBuilder<TimerDatabase>(
        name = dbFile,
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}
