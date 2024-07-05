package com.ssajudn.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ssajudn.expensetracker.data.local.entities.Saving
import com.ssajudn.expensetracker.data.local.entities.SavingsTarget
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingDao {
    @Insert
    suspend fun insertSaving(saving: Saving)

    @Insert
    suspend fun insertSavingsTarget(savingsTarget: SavingsTarget)

    @Query("SELECT DISTINCT title FROM savings")
    suspend fun getAllSavingsNames(): List<String>
}