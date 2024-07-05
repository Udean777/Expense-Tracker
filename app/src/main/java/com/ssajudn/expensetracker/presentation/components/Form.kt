package com.ssajudn.expensetracker.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.presentation.viewmodel.AddExpenseViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.UpdateField
import com.ssajudn.expensetracker.util.Common
import com.ssajudn.expensetracker.util.Utils

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DataForm(
    modifier: Modifier = Modifier,
    onAddExpenseClick: (model: Expense) -> Unit,
    viewModel: AddExpenseViewModel,
    navController: NavHostController
) {
    val dateDialogVisibility = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Add Transaction",
                                style = MaterialTheme.typography.titleSmall
                            )
                        },
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val model = Expense(
                                id = null,
                                title = viewModel.name.value,
                                amount = viewModel.amount.value.toDoubleOrNull() ?: 0.0,
                                date = Utils.formatDateToHumanReadableForm(viewModel.date.value),
                                category = viewModel.category.value,
                                type = viewModel.type.value,
                            )
                            onAddExpenseClick(model)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Save",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            FormSection(
                title = "Amount",
                content = {
                    OutlinedTextField(
                        value = viewModel.amount.value,
                        onValueChange = { viewModel.updateField(UpdateField.AMOUNT, it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Example: 10000",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                        },
                    )
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            FormSection(
                title = "Date",
                content = {
                    OutlinedTextField(
                        value = if (viewModel.date.value == 0L) "" else Utils.formatDateToHumanReadableForm(
                            viewModel.date.value
                        ),
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dateDialogVisibility.value = true },
                        placeholder = {
                            Text(
                                text = "Select Date",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                        },
                        readOnly = true,
                        enabled = false
                    )
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            FormSection(
                title = "Category",
                content = {
                    ExpenseDropDown(
                        listOfItems = Common.listProvider,
                        onItemSelected = { viewModel.updateField(UpdateField.CATEGORY, it) },
                        initialText = "Transaction Category",
                    )
                },
            )

            Spacer(modifier = Modifier.height(25.dp))

            FormSection(
                title = "Type",
                content = {
                    ExpenseDropDown(
                        listOfItems = listOf("Income", "Expense", "Savings"),
                        onItemSelected = { viewModel.updateField(UpdateField.TYPE, it) },
                        initialText = "Transaction Type"
                    )
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            FormSection(
                title = "Note",
                content = {
                    OutlinedTextField(
                        value = viewModel.name.value,
                        onValueChange = { viewModel.updateField(UpdateField.NAME, it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Example: My monthly salary",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                            )
                        },
                    )
                }
            )
        }

        if (dateDialogVisibility.value) {
            ExpenseDatePickerDialog(
                onDateSelected = {
                    viewModel.updateField(UpdateField.DATE, it)
                    dateDialogVisibility.value = false
                },
                onDismiss = { dateDialogVisibility.value = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    onDateSelected: (date: Long) -> Unit, onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(
    listOfItems: List<String>,
    onItemSelected: (item: String) -> Unit,
    initialText: String,
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(initialText) }
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        OutlinedTextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }) {
            listOfItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedItem.value = item
                        onItemSelected(item)
                        expanded.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun FormSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.size(4.dp))
        content()
    }
}