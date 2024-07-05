package com.ssajudn.expensetracker.domain.repository

import com.ssajudn.expensetracker.data.local.entities.Saving
import com.ssajudn.expensetracker.data.local.entities.SavingsTarget
import kotlinx.coroutines.flow.Flow

interface SavingRepository {
    suspend fun addSaving(saving: Saving)
    suspend fun addSavingsTarget(savingsTarget: SavingsTarget)
     fun getAllSavingsNames(): Flow<List<String>>
}