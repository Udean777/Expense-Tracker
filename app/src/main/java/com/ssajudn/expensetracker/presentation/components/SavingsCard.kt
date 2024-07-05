package com.ssajudn.expensetracker.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.util.Utils
import com.ssajudn.expensetracker.util.Utils.formatToDecimalValue

@Composable
fun SavingsCard(
    savings: Savings,
    onAddClick: (Savings?) -> Unit
) {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = savings.title, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = (savings.currentAmount / savings.targetAmount).toFloat(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${formatToDecimalValue(savings.currentAmount)}/${formatToDecimalValue(savings.targetAmount)}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onAddClick(savings)
            },
        ) {
            Text(text = "Add Amount")
        }
    }
}