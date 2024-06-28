package com.ssajudn.expensetracker.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssajudn.expensetracker.data.local.entities.Expense
import com.ssajudn.expensetracker.ui.theme.errorLight
import com.ssajudn.expensetracker.ui.theme.secondaryContainerDark
import com.ssajudn.expensetracker.ui.theme.secondaryDark
import com.ssajudn.expensetracker.ui.theme.secondaryLight
import com.ssajudn.expensetracker.ui.theme.success
import com.ssajudn.expensetracker.util.Utils

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.expenses.collectAsState()
    val expense = viewModel.getTotalExpense(state)
    val income = viewModel.getTotalIncome(state)
    val balance = viewModel.getBalance(state)
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CardItem(
                balance = balance,
                income = income,
                expense = expense
            )

            TransactionList(
                modifier = Modifier.fillMaxWidth(),
                list = state,
                topList = viewModel.getTopTransactions(state),
                onDelete = { expense ->
                    viewModel.deleteTransaction(expense)
                }
            )
        }
    }
}


@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    balance: String,
    income: String,
    expense: String
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(secondaryContainerDark)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column {
                Text(text = "Total Balance", fontSize = 16.sp)
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
                textColor = success
            )

            CardRowItem(
                title = "Expense",
                amount = expense,
                textColor = Color.Red
            )
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
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = amount,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    list: List<Expense>,
    topList: List<Expense>,
    onDelete: (Expense) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "See all",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (list.isEmpty()) {
                        Text(
                            text = "No recent transactions",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        list.forEach { item ->
                            Spacer(modifier = Modifier.size(8.dp))

                            TransactionItem(
                                title = item.title,
                                amount = Utils.formatToDecimalValue(item.amount),
                                date = item.date,
                                color = if (item.type == "Income") Color.Green else Color.Red,
                                onDelete = { onDelete(item) }
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Top Transactions",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "See all",
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (topList.isEmpty()) {
                        Text(
                            text = "No top transactions",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        topList.forEach { item ->
                            Spacer(modifier = Modifier.size(8.dp))

                            TransactionItem(
                                title = item.title,
                                amount = Utils.formatToDecimalValue(item.amount),
                                date = item.date,
                                color = if (item.type == "Income") Color.Green else Color.Red,
                                onDelete = { onDelete(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TransactionItem(
    title: String,
    amount: String,
    date: String,
    color: Color,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    text = date,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = amount,
                color = color,
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(onClick = onDelete) { // Tambahkan IconButton untuk aksi hapus
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = errorLight
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    HomeScreen(rememberNavController())
}