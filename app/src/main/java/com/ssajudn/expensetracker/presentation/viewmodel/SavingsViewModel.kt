package com.ssajudn.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.domain.repository.SavingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingsViewModel @Inject constructor(
    private val repository: SavingsRepository
) : ViewModel() {

    private val _savings = MutableStateFlow<List<Savings>>(emptyList())
    val savings: StateFlow<List<Savings>> = _savings.asStateFlow()

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

    fun updateSavingsAmount(id: Int, amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val savings = repository.getSavingsById(id)
            savings?.let {
                val updatedSavings = it.copy(currentAmount = it.currentAmount + amount)
                repository.updateSavings(updatedSavings)
            }
        }
    }
}