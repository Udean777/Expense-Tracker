package com.ssajudn.expensetracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssajudn.expensetracker.data.local.entities.Expense
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseDialog(
    expense: Expense,
    onConfirm: (Expense) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember {
        mutableStateOf(expense.title)
    }
    var amount by remember { mutableStateOf(expense.amount.toString()) }
    var category by remember { mutableStateOf(expense.category) }
    var date by remember { mutableStateOf(expense.date) }
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val updateTransaction = expense.copy(
                        title = title,
                        amount = amount.toDoubleOrNull() ?: expense.amount,
                        category = category,
                        date = date
                    )
                    onConfirm(updateTransaction)
                },
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "Edit Transaction")
        },
        text = {
            Column {
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Select Date: $date")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Note") },
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = {
                            showDatePicker = false
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    date = dateFormat.format(Date())
                                    showDatePicker = false
                                },
                            ) {
                                Text(text = "Save")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                },
                            ) {
                                Text(text = "Cancel")
                            }
                        },
                        content = {
                            DatePicker(state = rememberDatePickerState(initialSelectedDateMillis = Date().time))
                        }
                    )
                }
            }
        }
    )
}