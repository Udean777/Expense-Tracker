package com.ssajudn.expensetracker.domain.repository

import com.ssajudn.expensetracker.data.local.entities.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense): Boolean

    fun getAllExpense(): Flow<List<Expense>>

    fun getTopExpense(): Flow<List<Expense>>

    fun getTenTransaction(): Flow<List<Expense>>

    fun getCurrentMonthExpenses(): Flow<List<Expense>>

    fun getExpensesByMonth(yearMonth: String): Flow<List<Expense>>

    suspend fun deleteTransaction(expense: Expense)

    suspend fun updateTransaction(expense: Expense)

    suspend fun deleteExpensesBySavingsTitle(savingsTitle: String)
}