package com.ssajudn.expensetracker.data.repository

import com.ssajudn.expensetracker.data.local.ExpenseDao
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
): ExpenseRepository {
    override suspend fun addExpense(expense: Expense): Boolean {
        return try {
            expenseDao.insertExpense(expense)
            true
        } catch (ex: Throwable) {
            false
        }
    }

    override fun getAllExpense(): Flow<List<Expense>> {
        return expenseDao.getAllExpense()
    }
}