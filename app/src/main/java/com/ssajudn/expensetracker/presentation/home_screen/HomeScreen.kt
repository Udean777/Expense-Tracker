package com.ssajudn.expensetracker.presentation.home_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.expenses.collectAsState()
    val expense = viewModel.getTotalExpense(state)
    val income = viewModel.getTotalIncome(state)
    val balance = viewModel.getBalance(state)
    val isLoading by viewModel.isLoading.collectAsState()

    Log.d("HomeScreen", "Expenses: $state")
    Log.d("HomeScreen", "Income: $income, Expense: $expense, Balance: $balance")

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CardItem(
                balance = balance,
                income = income,
                expense = expense,
                title = "Your Balance this month"
            )

            TransactionList(
                modifier = Modifier.fillMaxWidth(),
                list = state,
                topList = viewModel.getTopTransactions(state),
                onDelete = { expense ->
                    viewModel.deleteTransaction(expense)
                }
            )
        }
    }
}