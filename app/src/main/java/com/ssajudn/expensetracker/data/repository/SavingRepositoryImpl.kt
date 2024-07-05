package com.ssajudn.expensetracker.data.repository

import com.ssajudn.expensetracker.data.local.SavingDao
import com.ssajudn.expensetracker.data.local.entities.Saving
import com.ssajudn.expensetracker.data.local.entities.SavingsTarget
import com.ssajudn.expensetracker.domain.repository.SavingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(
    private val savingDao: SavingDao
) : SavingRepository {
    override suspend fun addSaving(saving: Saving) {
        savingDao.insertSaving(saving)
    }

    override suspend fun addSavingsTarget(savingsTarget: SavingsTarget) {
        savingDao.insertSavingsTarget(savingsTarget)
    }

    override suspend fun getAllSavingsNames(): List<String> {
        return savingDao.getAllSavingsNames()
    }

}