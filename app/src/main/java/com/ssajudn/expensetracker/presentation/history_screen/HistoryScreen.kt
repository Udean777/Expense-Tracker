package com.ssajudn.expensetracker.presentation.history_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssajudn.expensetracker.presentation.components.EditExpenseDialog
import com.ssajudn.expensetracker.presentation.components.TopBar
import com.ssajudn.expensetracker.presentation.components.TransactionList
import com.ssajudn.expensetracker.presentation.viewmodel.HomeViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.HistoryViewModel
import com.ssajudn.expensetracker.util.getMonthName
import java.time.Instant
import java.util.Calendar
import java.util.Date

@SuppressLint("NewApi")
@Composable
fun HistoryScreen(
    navController: NavController
) {
    val viewModel: HistoryViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()

    val incomeListState = viewModel.incomeList.collectAsState(emptyList())
    val expenseListState = viewModel.expenseList.collectAsState(emptyList())

    val dateDialogVisibility = remember { mutableStateOf(false) }

    val selectedYearState = remember { mutableIntStateOf(0) }
    val selectedMonthState = remember { mutableIntStateOf(0) }

    val selectedDateText = remember {
        mutableStateOf("Get Monthly Data")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(navController = navController)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(selectedDateText.value)

            IconButton(
                onClick = {
                    dateDialogVisibility.value = true
                },
            ) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Date Picker")
            }
        }

        TransactionList(
            modifier = Modifier.fillMaxWidth(),
            list = incomeListState.value,
            topList = expenseListState.value,
            onDeleteTransaction = { transaction ->
                homeViewModel.deleteTransaction(transaction)
            },
            firstListTitle = "Income",
            secondListTitle = "Expense",
            onEditClick = { transaction ->
                homeViewModel.showEditDialog(transaction)
            }
        )

        if (homeViewModel.isEditDialogVisible.collectAsState().value) {
            val transactionToEdit = homeViewModel.transactionToEdit.collectAsState().value
            if (transactionToEdit != null) {
                EditExpenseDialog(
                    expense = transactionToEdit,
                    onConfirm = { updatedExpense ->
                        homeViewModel.updateTransaction(updatedExpense)
                    },
                    onDismiss = { homeViewModel.hideEditDialog() }
                )
            }
        }

        if (dateDialogVisibility.value) {
            PickerDialog(
                onDateSelected = { year, month ->
                    viewModel.getMonthlyData(year, month)
                    selectedYearState.intValue = year
                    selectedMonthState.intValue = month
                    selectedDateText.value = "Monthly Data for ${getMonthName(month)} $year"
                    dateDialogVisibility.value = false

                },
                onDismiss = { dateDialogVisibility.value = false }
            )
        }
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerDialog(
    onDateSelected: (year: Int, month: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Date().time)
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                val selectedDateMillis = datePickerState.selectedDateMillis
                Instant.ofEpochMilli(selectedDateMillis!!)
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selectedDateMillis
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH) + 1
                onDateSelected(year, month)
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}