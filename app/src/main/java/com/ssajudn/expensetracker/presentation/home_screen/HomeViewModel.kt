package com.ssajudn.expensetracker.presentation.home_screen

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssajudn.expensetracker.R
import com.ssajudn.expensetracker.data.local.AppDB
import com.ssajudn.expensetracker.data.local.dao.ExpenseDao
import com.ssajudn.expensetracker.domain.model.Expense
import com.ssajudn.expensetracker.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expenseDao: ExpenseDao
) : ViewModel() {
    val expenses = expenseDao.getAllExpense()

    fun getBalance(expenses: List<Expense>): String {
        val balance = expenses.sumOf { expense ->
            if (expense.type == "Income") expense.amount else -expense.amount
        }
        return Utils.formatToDecimalValue(balance)
    }

    fun getTotalExpense(expenses: List<Expense>): String {
        val totalExpense = expenses.filter { it.type == "Expense" }
            .sumOf { it.amount }
        return Utils.formatToDecimalValue(totalExpense)
    }

    fun getTotalIncome(expenses: List<Expense>): String {
        val totalIncome = expenses.filter { it.type == "Income" }
            .sumOf { it.amount }
        return Utils.formatToDecimalValue(totalIncome)
    }

    fun getItemIcon(expense: Expense): Int {
        return when (expense.category) {
            "Paypal" -> R.drawable.ic_paypal
            "Netflix" -> R.drawable.ic_netflix
            "Starbucks" -> R.drawable.ic_starbucks
            else -> R.drawable.ic_upwork
        }
    }
}