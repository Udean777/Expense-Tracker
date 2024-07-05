package com.ssajudn.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ssajudn.expensetracker.data.local.entities.Savings
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavings(savings: Savings)

    @Update
    suspend fun updateSavings(savings: Savings)

    @Delete
    suspend fun deleteSavings(savings: Savings)

    @Query("SELECT * FROM savings")
    fun getAllSavings(): Flow<List<Savings>>

    @Query("SELECT * FROM savings WHERE id = :id")
    suspend fun getSavingsById(id: Int): Savings?
}