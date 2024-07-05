package com.ssajudn.expensetracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ssajudn.expensetracker.presentation.viewmodel.SavingsViewModel

@Composable
fun AddSavingsDialog(
    onDismiss: () -> Unit,
    viewModel: SavingsViewModel
) {
    var name by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Savings Target") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Target Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = targetAmount,
                    onValueChange = { targetAmount = it },
                    label = { Text("Target Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = targetAmount.toDoubleOrNull() ?: 0.0
                    viewModel.addSavings(name, amount)
                    onDismiss()
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}