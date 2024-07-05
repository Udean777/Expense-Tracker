package com.ssajudn.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name = _name

    private val _amount = mutableStateOf("")
    val amount = _amount

    private val _date = mutableStateOf(0L)
    val date = _date

    private val _category = mutableStateOf("")
    val category = _category

    private val _type = mutableStateOf("")
    val type = _type

    suspend fun addExpense(expenseEntity: Expense): Boolean {
        return expenseRepository.addExpense(expenseEntity)
    }

    fun updateField(field: UpdateField, value: Any){
        when(field){
            UpdateField.NAME -> if (value is String) _name.value = value
            UpdateField.AMOUNT -> if (value is String) _amount.value = value
            UpdateField.DATE -> if (value is Long) _date.value = value
            UpdateField.CATEGORY -> if (value is String) _category.value = value
            UpdateField.TYPE -> if (value is String) _type.value = value
        }
    }
}

enum class UpdateField {
    NAME,
    AMOUNT,
    DATE,
    CATEGORY,
    TYPE
}
