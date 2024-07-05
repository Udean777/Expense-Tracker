package com.ssajudn.expensetracker.presentation.components

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
            Text(text = "Add Amount")
        },
        text = {
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(text = "Amount") },
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