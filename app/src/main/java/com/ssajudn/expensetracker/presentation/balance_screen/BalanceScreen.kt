package com.ssajudn.expensetracker.presentation.balance_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssajudn.expensetracker.presentation.components.AddTargetDialog
import com.ssajudn.expensetracker.presentation.components.CardItem
import com.ssajudn.expensetracker.presentation.components.TopBar
import com.ssajudn.expensetracker.presentation.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BalanceScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.expenses.collectAsState(initial = emptyList())
    val expense = viewModel.getTotalExpense(state.value)
    val income = viewModel.getTotalIncome(state.value)
    val balance = viewModel.getBalance(state.value)

    val showAddTargetDialog = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(navController = navController)

        CardItem(
            balance = balance,
            income = income,
            expense = expense,
            title = "Total Balance"
        )
    }
}
