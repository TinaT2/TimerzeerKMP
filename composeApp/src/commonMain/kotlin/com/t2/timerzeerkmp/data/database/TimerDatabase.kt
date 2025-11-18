@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.t2.timerzeerkmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.t2.timerzeerkmp.data.database.dao.TimerDao
import com.t2.timerzeerkmp.data.database.entity.TimerEntity

@Database(entities = [TimerEntity::class], version = 1)
@TypeConverters(TimerTypeConvertor::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class TimerDatabase : RoomDatabase(){
    abstract fun timerDao(): TimerDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<TimerDatabase> {
    override fun initialize(): TimerDatabase
}
