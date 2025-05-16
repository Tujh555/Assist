package com.example.assist.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assist.data.database.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM EXPENSES WHERE car_id = :carId")
    fun observe(carId: Long) : Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun delete(id: Long)
}