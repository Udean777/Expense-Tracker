package com.ssajudn.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import com.ssajudn.expensetracker.util.Utils.formatToDecimalValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {
    private val _balance = MutableStateFlow("")
    val balance: StateFlow<String> = _balance

    private val _income = MutableStateFlow("")
    val income: StateFlow<String> = _income

    private val _expense = MutableStateFlow("")
    val expense: StateFlow<String> = _expense

    private val _incomeList = MutableStateFlow<List<Expense>>(emptyList())
    val incomeList: StateFlow<List<Expense>> = _incomeList

    private val _expenseList = MutableStateFlow<List<Expense>>(emptyList())
    val expenseList: StateFlow<List<Expense>> = _expenseList

    private val _selectedYear = MutableStateFlow(2024)

    private val _selectedMonth = MutableStateFlow(7)

    init {
        getMonthlyData(_selectedYear.value, _selectedMonth.value)
    }

    fun getMonthlyData(year: Int = _selectedYear.value, month: Int = _selectedMonth.value) {
        viewModelScope.launch {
            expenseRepository.getExpensesByMonth(
                "${year}-${String.format("%02d", month)}"
            ).collect { transactions ->
                val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.amount }
                val totalExpense = transactions.filter { it.type == "Expense" }.sumOf { it.amount }

                _balance.value = formatToDecimalValue(totalIncome - totalExpense)
                _income.value = formatToDecimalValue(totalIncome)
                _expense.value = formatToDecimalValue(totalExpense)

                _incomeList.value = transactions.filter {
                    it.type == "Income"
                }

                _expenseList.value = transactions.filter {
                    it.type == "Expense"
                }
            }
        }
    }
}
