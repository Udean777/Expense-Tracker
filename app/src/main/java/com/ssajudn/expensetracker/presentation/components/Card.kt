package com.ssajudn.expensetracker.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssajudn.expensetracker.ui.theme.Expense
import com.ssajudn.expensetracker.ui.theme.Income


@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    balance: String,
    income: String,
    expense: String,
    title: String
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
                    Text(text = title, fontSize = 16.sp)
                    Text(
                        text = balance, fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardRowItem(
                    title = "Income",
                    amount = income,
                    textColor = Income
                )

                CardRowItem(
                    title = "Expense",
                    amount = expense,
                    textColor = Expense
                )
            }
        }
    }
}


@Composable
fun CardRowItem(
    modifier: Modifier = Modifier,
    title: String,
    amount: String,
    textColor: Color
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(3.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = amount,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}