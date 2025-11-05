package com.t2.timerzeerkmp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.t2.timerzeerkmp.data.database.dao.TimerDao
import com.t2.timerzeerkmp.data.database.entity.TimerEntity

@Database(entities = [TimerEntity::class], version = 1)
@TypeConverters(TimerTypeConvertor::class)
abstract class TimerDatabase : RoomDatabase(){
    abstract fun timerDao(): TimerDao
}

expect fun createTimerDatabase(): TimerDatabase
