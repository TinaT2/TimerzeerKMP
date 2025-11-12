package com.t2.timerzeerkmp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.t2.timerzeerkmp.data.database.entity.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timer: TimerEntity)

    @Query("SELECT * FROM TimerEntity ORDER BY id DESC")
    fun getAllTimers(): Flow<List<TimerEntity>>

    @Query("Select count(*) from TimerEntity")
    fun getCounter(): Flow<Int>
}