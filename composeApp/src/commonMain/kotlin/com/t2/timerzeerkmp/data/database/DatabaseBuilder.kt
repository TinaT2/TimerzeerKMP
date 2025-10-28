package com.t2.timerzeerkmp.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
//
//fun getRoomDatabase(
//    builder: RoomDatabase.Builder<TimerDatabase>
//): TimerDatabase {
//    return builder
//        .setDriver(BundledSQLiteDriver())
//        .setQueryCoroutineContext(Dispatchers.IO)
//        .build()
//}