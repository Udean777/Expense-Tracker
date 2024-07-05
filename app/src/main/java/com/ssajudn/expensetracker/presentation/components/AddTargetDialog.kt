package com.ssajudn.expensetracker.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ssajudn.expensetracker.util.Utils

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddTargetDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onAddTarget: (name: String, amount: Double, date: Long) -> Unit
) {
    val targetName = remember {
        mutableStateOf("")
    }
    val targetAmount = remember { mutableStateOf("") }
    val targetDate = remember { mutableStateOf(0L) }
    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        ExpenseDatePickerDialog(
            onDateSelected = {
                targetDate.value = it
                showDatePicker.value = false
            },
            onDismiss = {
                showDatePicker.value = false
            }
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 24.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Add Savings Target", style = MaterialTheme.typography.titleSmall)
                OutlinedTextField(
                    value = targetName.value,
                    onValueChange = { targetName.value = it },
                    label = { Text("Target Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = targetAmount.value,
                    onValueChange = { targetAmount.value = it },
                    label = { Text("Target Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = if (targetDate.value == 0L) "" else Utils.formatDateToHumanReadableForm(
                        targetDate.value
                    ),
                    onValueChange = {},
                    label = { Text("Target Date") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker.value = true },
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    TextButton(onClick = {
                        onAddTarget(
                            targetName.value,
                            targetAmount.value.toDoubleOrNull() ?: 0.0,
                            targetDate.value
                        )
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}