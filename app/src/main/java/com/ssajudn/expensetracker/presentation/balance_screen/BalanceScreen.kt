package com.ssajudn.expensetracker.presentation.balance_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.presentation.components.AddAmountDialog
import com.ssajudn.expensetracker.presentation.components.AddSavingsDialog
import com.ssajudn.expensetracker.presentation.components.SavingsCard
import com.ssajudn.expensetracker.presentation.components.SavingsHeader
import com.ssajudn.expensetracker.presentation.viewmodel.HomeViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.SavingsViewModel
import com.ssajudn.expensetracker.util.Utils

@Composable
fun BalanceScreen(
    viewModel: SavingsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val savingList by viewModel.savings.collectAsState()
    val errorMessage by viewModel.errorMsg.collectAsState()
    var showAddSavingsDialog by remember {
        mutableStateOf(false)
    }
    var selectedSavings by remember {
        mutableStateOf<Savings?>(null)
    }
    var showDeleteConfirmDialog by remember {
        mutableStateOf<Savings?>(null)
    }
    val state = homeViewModel.expenses.collectAsState(initial = emptyList())
    val stateMonth by homeViewModel.currentMonthExpenses.collectAsState(initial = emptyList())
    val balance = homeViewModel.getBalance(state.value)
    val balanceThisMonth = homeViewModel.getBalance(stateMonth)
    val context = LocalContext.current

    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearErrorMessage()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(150.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column {
                        Text(text = "Balance", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = Utils.formatToDecimalValue(balance),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Column {
                        Text(
                            text = "Your Balance this Month",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = Utils.formatToDecimalValue(balanceThisMonth),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        )
                    }
                }
            }
        }

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
                        selectedSavings = clickedSavings
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
                viewModel.updateSavingsAmount(
                    savings.id,
                    amount = amount,
                    balance
                )
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
                Text(
                    text = "Delete Savings",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to delete this savings?",
                    style = MaterialTheme.typography.bodyMedium
                )
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