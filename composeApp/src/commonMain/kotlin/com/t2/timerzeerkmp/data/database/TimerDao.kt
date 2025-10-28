package com.t2.timerzeerkmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao{
    @Insert
    fun insert(timer: TimerEntity)

    @Query("SELECT * FROM TimerEntity ORDER BY id DESC")
    fun getAllTimers(): Flow<List<TimerEntity>>

    @Query("Select count(*) from TimerEntity")
    fun getCounter(): Flow<Int>
}