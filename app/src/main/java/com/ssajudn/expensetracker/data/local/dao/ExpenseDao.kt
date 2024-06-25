package com.ssajudn.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ssajudn.expensetracker.domain.model.Expense
import com.ssajudn.expensetracker.domain.model.ExpenseSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAllExpense(): Flow<List<Expense>>

    @Query("SELECT type, date, SUM(amount) AS total_amount FROM expense GROUP BY type, date ORDER BY date DESC")
    fun getAllExpenseByDate(): Flow<List<ExpenseSummary>>

    @Insert
    suspend fun insertExpense(expenseEntity: Expense)

    @Delete
    suspend fun deleteExpense(expenseEntity: Expense)

    @Update
    suspend fun updateExpense(expenseEntity: Expense)

}