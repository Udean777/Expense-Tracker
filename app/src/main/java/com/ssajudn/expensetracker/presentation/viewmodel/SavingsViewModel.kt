package com.ssajudn.expensetracker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import com.ssajudn.expensetracker.domain.repository.SavingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SavingsViewModel @Inject constructor(
    private val repository: SavingsRepository,
    private val expenseRepository: ExpenseRepository,
) : ViewModel() {

    private val _savings = MutableStateFlow<List<Savings>>(emptyList())
    val savings: StateFlow<List<Savings>> = _savings.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllSavings().collect { savingsList ->
                _savings.value = savingsList
            }
        }

    }

    fun addSavings(name: String, targetAmount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSavings = Savings(title = name, targetAmount = targetAmount)
            repository.insertSavings(newSavings)
        }
    }

    fun updateSavingsAmount(id: Int, amount: Double, currentBalance: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentBalance < amount) {
                _errorMsg.value = "Insufficient Balance"
                return@launch
            } else {
                val savings = repository.getSavingsById(id)
                savings?.let {
                    val updatedSavings = it.copy(currentAmount = it.currentAmount + amount)
                    repository.updateSavings(updatedSavings)

                    val savingTransaction = Expense(
                        id = null,
                        title = "Savings: ${it.title}",
                        amount = amount,
                        date = getCurrentDate(),
                        category = "Savings",
                        type = "Expense"
                    )
                    expenseRepository.addExpense(savingTransaction)
                }
            }
        }
    }

    fun clearErrorMessage() {
        _errorMsg.value = null
    }

    fun decreaseSavingsAmount(savingsTitle: String, amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val savings = repository.getSavingsByTitle(savingsTitle)
            savings?.let {
                val updatedAmount = (it.currentAmount - amount).coerceAtLeast(0.0)
                val updatedSavings = it.copy(currentAmount = updatedAmount)
                repository.updateSavings(updatedSavings)
            }
        }
    }

    fun deleteSavings(savings: Savings) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSavings(savings)

            expenseRepository.deleteExpensesBySavingsTitle("Savings: ${savings.title}")

            val refundExpense = Expense(
                id = null,
                title = "Refund: ${savings.title}",
                amount = savings.currentAmount,
                date = getCurrentDate(),
                category = "Savings Refund",
                type = "Income"
            )
            expenseRepository.addExpense(refundExpense)
        }
    }
}

private fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date())
}

sealed class SavingsResult {
    object Success : SavingsResult()
    object InsufficientBalance : SavingsResult()
}