package com.ssajudn.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.model.ExpenseSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun getAllExpense(): Flow<List<Expense>>

    @Query("SELECT type, date, SUM(amount) AS total_amount FROM expense GROUP BY type, date ORDER BY date")
    fun getAllExpenseByDate(): Flow<List<ExpenseSummary>>

    @Query("SELECT * FROM expense WHERE strftime('%Y-%m', substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2)) = strftime('%Y-%m', 'now')")
    fun getCurrentMonthExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE strftime('%Y-%m', substr(date, 7, 4) || '-' || substr(date, 4, 2) || '-' || substr(date, 1, 2)) = :yearMonth")
    fun getExpensesByMonth(yearMonth: String): Flow<List<Expense>>

    @Insert
    suspend fun insertExpense(expenseEntity: Expense)

    @Delete
    suspend fun deleteExpense(expenseEntity: Expense)

    @Update
    suspend fun updateExpense(expenseEntity: Expense)
}
