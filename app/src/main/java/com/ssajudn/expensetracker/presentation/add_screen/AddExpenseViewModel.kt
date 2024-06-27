package com.ssajudn.expensetracker.presentation.add_screen

import androidx.lifecycle.ViewModel
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    suspend fun addExpense(expenseEntity: Expense): Boolean {
        return expenseRepository.addExpense(expenseEntity)
    }

}