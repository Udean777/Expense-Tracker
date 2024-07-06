package com.ssajudn.expensetracker.presentation.balance_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.presentation.components.AddAmountDialog
import com.ssajudn.expensetracker.presentation.components.AddSavingsDialog
import com.ssajudn.expensetracker.presentation.components.CardItem
import com.ssajudn.expensetracker.presentation.components.SavingsCard
import com.ssajudn.expensetracker.presentation.components.SavingsHeader
import com.ssajudn.expensetracker.presentation.components.TopBar
import com.ssajudn.expensetracker.presentation.viewmodel.HomeViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.SavingsViewModel

@Composable
fun BalanceScreen(
    viewModel: SavingsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val savingList by viewModel.savings.collectAsState()
    var showAddSavingsDialog by remember {
        mutableStateOf(false)
    }
    var selectedSavings by remember {
        mutableStateOf<Savings?>(null)
    }
    var showDeleteConfirmDialog by remember {
        mutableStateOf<Savings?>(null)
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CardItem(
            balance = homeViewModel.balance,
            income = homeViewModel.income,
            expense = homeViewModel.expense,
            title = "Balance"
        )

        SavingsHeader(
            onAddClick = {
                showAddSavingsDialog = true
            },
        )

        LazyColumn {
            items(savingList) { savings ->
                SavingsCard(
                    savings = savings,
                    onAddClick = { clickedSavings ->
                        selectedSavings = savings
                    },
                    onDeleteClick = { savingsToDelete ->
                        showDeleteConfirmDialog = savingsToDelete
                    }
                )
            }
        }
    }

    if (showAddSavingsDialog) {
        AddSavingsDialog(
            onDismiss = { showAddSavingsDialog = false },
            viewModel = viewModel
        )
    }

    selectedSavings?.let { savings ->
        AddAmountDialog(
            onDismiss = { selectedSavings = null },
            onConfirm = { amount ->
                viewModel.updateSavingsAmount(savings.id ?: return@AddAmountDialog, amount)
                selectedSavings = null
            },
        )
    }

    showDeleteConfirmDialog?.let { savingsToDelete ->
        AlertDialog(
            onDismissRequest = {
                showDeleteConfirmDialog = null
            },
            title = {
                Text(text = "Delete Savings")
            },
            text = {
                Text(text = "Are you sure you want to delete this savings?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteSavings(savingsToDelete)
                        showDeleteConfirmDialog = null
                    },
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmDialog = null }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}