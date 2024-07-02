package com.ssajudn.expensetracker.presentation.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssajudn.expensetracker.presentation.components.CardItem
import com.ssajudn.expensetracker.presentation.components.TopBar
import com.ssajudn.expensetracker.presentation.components.TransactionList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.currentMonthExpenses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val currentDate = remember {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        dateFormat.format(Date())
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() 
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(navController = navController)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currentDate,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )

            CardItem(
                balance = viewModel.balance,
                income = viewModel.income,
                expense = viewModel.expense,
                title = "Your Balance this month"
            )

            TransactionList(
                modifier = Modifier.fillMaxWidth(),
                list = state,
                topList = viewModel.getTopTransactions(state),
                onDeleteTransaction = { expense ->
                    viewModel.deleteTransaction(expense)
                }
            )
        }
    }
}