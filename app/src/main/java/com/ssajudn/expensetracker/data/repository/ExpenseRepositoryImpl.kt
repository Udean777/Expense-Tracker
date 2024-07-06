package com.ssajudn.expensetracker.data.repository

import com.ssajudn.expensetracker.data.local.ExpenseDao
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override suspend fun addExpense(expense: Expense): Boolean {
        return try {
            expenseDao.insertExpense(expense)
            true
        } catch (ex: Throwable) {
            false
        }
    }

    override fun getAllExpense(): Flow<List<Expense>> {
        return expenseDao.getAllExpense().map { expense ->
            expense.sortedByDescending { it.date }
        }
    }

    override fun getTopExpense(): Flow<List<Expense>> {
        return expenseDao.getAllExpense().map { expense ->
            expense.sortedByDescending { it.amount }.take(5)
        }
    }

    override fun getTenTransaction(): Flow<List<Expense>> {
        return expenseDao.getAllExpense().map { expense ->
            expense.sortedByDescending { it.date }.take(10)
        }
    }

    override fun getCurrentMonthExpenses(): Flow<List<Expense>> {
        return expenseDao.getCurrentMonthExpenses()
    }

    override fun getExpensesByMonth(yearMonth: String): Flow<List<Expense>> {
        return expenseDao.getExpensesByMonth(yearMonth)
    }

    override suspend fun deleteTransaction(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    override suspend fun updateTransaction(expense: Expense) {
        expenseDao.updateExpense(expense)
    }

    override suspend fun deleteExpensesBySavingsTitle(savingsTitle: String) {
        expenseDao.deleteExpensesBySavingsTitle(savingsTitle)
    }
}