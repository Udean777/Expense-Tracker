package com.ssajudn.expensetracker.presentation.add_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddExpense(navController: NavController) {
    val viewModel: AddExpenseViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        DataForm(
            onAddExpenseClick = { expense ->
                coroutineScope.launch {
                    if (viewModel.addExpense(expense)) {
                        navController.popBackStack()
                    }
                }
            },
            viewModel = viewModel
        )
    }
}