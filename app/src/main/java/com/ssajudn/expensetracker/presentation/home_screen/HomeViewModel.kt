package com.ssajudn.expensetracker.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.R
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import com.ssajudn.expensetracker.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.exp

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getExpenses()
    }

    private fun getCurrentMonthExpenses() {
        viewModelScope.launch {
            expenseRepository.getCurrentMonthTransactions().collect { transactionList ->
                _expenses.value = transactionList
                _isLoading.value = false
            }
        }
    }

    private fun getExpenses() {
        viewModelScope.launch {
            expenseRepository.getAllExpense().collect { expenseList ->
                _expenses.value = expenseList
                _isLoading.value = false
            }
        }
    }

    fun deleteTransaction(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteTransaction(expense)
        }
    }

    fun getTopTransactions(expenses: List<Expense>, limit: Int = 5): List<Expense> {
        return expenses.sortedByDescending { it.amount }.take(limit)
    }

    fun getTotalIncome(expenses: List<Expense>): String {
        val income = expenses.filter { it.type == "Income" }.sumOf { it.amount }
        return Utils.formatToDecimalValue(income)
    }

    fun getTotalExpense(expenses: List<Expense>): String {
        val expense = expenses.filter { it.type == "Expense" }.sumOf { it.amount }
        return Utils.formatToDecimalValue(expense)
    }

    fun getBalance(expenses: List<Expense>): String {
        val balance = expenses.filter { it.type == "Income" }.sumOf { it.amount } -
                expenses.filter { it.type == "Expense" }.sumOf { it.amount }
        return Utils.formatToDecimalValue(balance)
    }
}
