package com.ssajudn.expensetracker.presentation.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.ssajudn.expensetracker.util.Utils

@Composable
fun AddAmountDialog(
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit,
) {
    var amount by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Savings Amount",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(
                    text = "How much would you want to add?",
                    style = MaterialTheme.typography.labelMedium
                ) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    amount.toDoubleOrNull()?.let {
                        onConfirm(it)
                        onDismiss()
                    }
                },
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}