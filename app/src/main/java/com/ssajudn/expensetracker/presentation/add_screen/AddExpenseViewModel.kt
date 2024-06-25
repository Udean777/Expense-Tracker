package com.ssajudn.expensetracker.presentation.add_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.data.local.dao.ExpenseDao
import com.ssajudn.expensetracker.domain.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseDao: ExpenseDao
) : ViewModel() {

    suspend fun addExpense(expenseEntity: Expense): Boolean {
        return try {
            expenseDao.insertExpense(expenseEntity)
            true
        } catch (ex: Throwable) {
            false
        }
    }
}