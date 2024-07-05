package com.ssajudn.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.data.local.entities.Saving
import com.ssajudn.expensetracker.data.local.entities.SavingsTarget
import com.ssajudn.expensetracker.domain.repository.SavingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSavingsViewModel @Inject constructor(
    private val savingRepository: SavingRepository
) : ViewModel() {
    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _date = MutableStateFlow(0L)
    val date: StateFlow<Long> = _date

    private val _selectedSavingName = MutableStateFlow("")
    val selectedSavingName: StateFlow<String> = _selectedSavingName

    private val _targetDate = MutableStateFlow(0L)
    val targetDate: StateFlow<Long> = _targetDate

    private val _goal = MutableStateFlow("")
    val goal: StateFlow<String> = _goal

    fun updateField(field: UpdateFieldSaving, value: String) {
        when (field) {
            UpdateFieldSaving.AMOUNT -> _amount.value = value
            UpdateFieldSaving.DATE -> _date.value = value.toLong()
            UpdateFieldSaving.SAVING_NAME -> _selectedSavingName.value = value
            UpdateFieldSaving.TARGET_DATE -> _targetDate.value = value.toLong()
            UpdateFieldSaving.GOAL -> _goal.value = value
            else -> {}
        }
    }

    fun getSavingsNames() = savingRepository.getAllSavingsNames()

    fun addSaving(saving: Saving) = viewModelScope.launch {
        savingRepository.addSaving(saving)
    }

    fun addSavingsTarget(name: String, amount: Double, date: Long) = viewModelScope.launch {
        savingRepository.addSavingsTarget(
            SavingsTarget(
                name = name,
                targetAmount = amount,
                targetDate = date.toString()
            )
        )
    }
}

enum class UpdateFieldSaving {
    AMOUNT,
    DATE,
    SAVING_NAME,
    TARGET_DATE,
    GOAL
}