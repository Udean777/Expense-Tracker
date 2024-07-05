package com.ssajudn.expensetracker.data.repository

import com.ssajudn.expensetracker.data.local.AppDB
import com.ssajudn.expensetracker.data.local.SavingsDao
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.domain.repository.SavingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavingsRepositoryImpl @Inject constructor(
    private val savingsDao: SavingsDao
) : SavingsRepository {

    override suspend fun insertSavings(savings: Savings) = withContext(Dispatchers.IO) {
        savingsDao.insertSavings(savings)
    }

    override suspend fun updateSavings(savings: Savings) = withContext(Dispatchers.IO) {
        savingsDao.updateSavings(savings)
    }

    override suspend fun deleteSavings(savings: Savings) = withContext(Dispatchers.IO) {
        savingsDao.deleteSavings(savings)
    }

    override fun getAllSavings(): Flow<List<Savings>> = savingsDao.getAllSavings()

    override suspend fun getSavingsById(id: Int): Savings? = withContext(Dispatchers.IO) {
        savingsDao.getSavingsById(id)
    }
}