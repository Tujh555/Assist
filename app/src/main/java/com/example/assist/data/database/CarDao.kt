package com.example.assist.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars WHERE id = :id")
    fun observe(id: Long): Flow<CarEntity?>

    @Query("SELECT * FROM cars")
    fun observeAll(): Flow<List<CarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CarEntity)

    @Query("DELETE FROM cars WHERE id = :id")
    suspend fun delete(id: Long)
}