package com.ssajudn.expensetracker.domain.repository

import com.ssajudn.expensetracker.data.local.entities.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense): Boolean

    fun getAllExpense(): Flow<List<Expense>>
}