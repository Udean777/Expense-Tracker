package com.ssajudn.expensetracker.presentation.add_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.util.Utils
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddExpense(navController: NavController) {
    val viewModel: AddExpenseViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AddTopBar(
                onBackButtonClick = {
                    navController.navigateUp()
                }
            )
        }
    ) { paddingValues ->
        DataForm(
            onAddExpenseClick = { expense ->
                coroutineScope.launch {
                    if (viewModel.addExpense(expense)) {
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTopBar(
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Add Expense/Income",
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DataForm(
    modifier: Modifier,
    onAddExpenseClick: (model: Expense) -> Unit,
    viewModel: AddExpenseViewModel
) {
    val dateDialogVisibility = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Name", fontSize = 14.sp)

        Spacer(modifier = Modifier.size(4.dp))

        TextField(
            value = viewModel.name.value,
            onValueChange = { viewModel.updateField(UpdateField.NAME, it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Name", fontSize = 15.sp) },
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = "Amount", fontSize = 14.sp)

        Spacer(modifier = Modifier.size(4.dp))

        TextField(
            value = viewModel.amount.value,
            onValueChange = { viewModel.updateField(UpdateField.AMOUNT, it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Amount", fontSize = 15.sp) },
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = "Date", fontSize = 14.sp)

        Spacer(modifier = Modifier.size(4.dp))

        TextField(
            value = if (viewModel.date.value == 0L) "" else Utils.formatDateToHumanReadableForm(
                viewModel.date.value
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            placeholder = { Text(text = "Date", fontSize = 15.sp) },
            readOnly = true,
            enabled = false
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = "Category", fontSize = 14.sp)

        Spacer(modifier = Modifier.size(4.dp))

        ExpenseDropDown(
            listOf("Netflix", "Paypal", "Starbucks", "Salary", "Upwork"),
            onItemSelected = { viewModel.updateField(UpdateField.CATEGORY, it) }
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = "Type", fontSize = 14.sp)

        Spacer(modifier = Modifier.size(4.dp))

        ExpenseDropDown(
            listOf("Income", "Expense"),
            onItemSelected = { viewModel.updateField(UpdateField.TYPE, it) }
        )

        Spacer(modifier = Modifier.size(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
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
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "Add",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
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
fun ExpenseDropDown(listOfItems: List<String>, onItemSelected: (item: String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(listOfItems[0]) }
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        TextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            }
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
