package com.ssajudn.expensetracker.presentation.balance_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssajudn.expensetracker.data.local.entities.Savings
import com.ssajudn.expensetracker.presentation.components.AddAmountDialog
import com.ssajudn.expensetracker.presentation.components.AddSavingsDialog
import com.ssajudn.expensetracker.presentation.components.CardItem
import com.ssajudn.expensetracker.presentation.components.SavingsCard
import com.ssajudn.expensetracker.presentation.components.SavingsHeader
import com.ssajudn.expensetracker.presentation.components.TopBar
import com.ssajudn.expensetracker.presentation.viewmodel.HomeViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.SavingsViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BalanceScreen(
    modifier: Modifier = Modifier,
    viewModel: SavingsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val savingList by viewModel.savings.collectAsState()
    var showAddSavingsDialog by remember {
        mutableStateOf(false)
    }
    var selectedSavings by remember {
        mutableStateOf<Savings?>(null)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(navController = navController)

        SavingsHeader(
            onAddClick = {
                showAddSavingsDialog = true
            },
        )

        LazyColumn {
            items(savingList) { savings ->
                SavingsCard(
                    savings = savings,
                    onAddClick = { clickedSavings ->
                        selectedSavings = savings
                    }
                )
            }
        }
    }

    if (showAddSavingsDialog) {
        AddSavingsDialog(
            onDismiss = { showAddSavingsDialog = false },
            viewModel = viewModel
        )
    }

    selectedSavings?.let { savings ->
        AddAmountDialog(
            onDismiss = { selectedSavings = null },
            onConfirm = { amount ->
                viewModel.updateSavingsAmount(savings.id, amount)
                selectedSavings = null
            },
        )
    }
}