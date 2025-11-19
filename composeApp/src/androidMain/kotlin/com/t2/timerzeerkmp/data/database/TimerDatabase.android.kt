package com.t2.timerzeerkmp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context):  RoomDatabase.Builder<TimerDatabase> {
    val dbFile = context.getDatabasePath("timer.db")
    return Room.databaseBuilder<TimerDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}