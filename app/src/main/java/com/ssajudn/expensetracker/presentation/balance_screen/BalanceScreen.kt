package com.ssajudn.expensetracker.presentation.balance_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssajudn.expensetracker.presentation.home_screen.CardItem
import com.ssajudn.expensetracker.presentation.home_screen.HomeViewModel

@Composable
fun BalanceScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.expenses.collectAsState(initial = emptyList())
    val expense = viewModel.getTotalExpense(state.value)
    val income = viewModel.getTotalIncome(state.value)
    val balance = viewModel.getBalance(state.value)

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CardItem(
            balance = balance,
            income = income,
            expense = expense
        )
    }
}