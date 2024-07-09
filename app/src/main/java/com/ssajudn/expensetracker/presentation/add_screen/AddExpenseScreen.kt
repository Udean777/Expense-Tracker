package com.ssajudn.expensetracker.presentation.add_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssajudn.expensetracker.presentation.components.DataForm
import com.ssajudn.expensetracker.presentation.viewmodel.AddExpenseViewModel
import kotlinx.coroutines.launch

@Composable
fun AddExpense(navController: NavHostController) {
    val viewModel: AddExpenseViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    DataForm(
        onAddExpenseClick = { expense ->
            coroutineScope.launch {
                if (viewModel.addExpense(expense)) {
                    navController.popBackStack()
                }
            }
        },
        viewModel = viewModel,
        navController = navController
    )
}