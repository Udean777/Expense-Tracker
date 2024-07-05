package com.ssajudn.expensetracker.domain.repository

import com.ssajudn.expensetracker.data.local.entities.Savings
import kotlinx.coroutines.flow.Flow

interface SavingsRepository {
    suspend fun insertSavings(savings: Savings)

    suspend fun updateSavings(savings: Savings)

    suspend fun deleteSavings(savings: Savings)

    fun getAllSavings(): Flow<List<Savings>>

    suspend fun getSavingsById(id: Int): Savings?
}