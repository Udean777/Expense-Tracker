package com.ssajudn.expensetracker.presentation.add_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssajudn.expensetracker.presentation.components.DataForm
import com.ssajudn.expensetracker.presentation.viewmodel.AddExpenseViewModel
import com.ssajudn.expensetracker.presentation.viewmodel.AddSavingsViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AddExpense(navController: NavHostController) {
    val viewModel: AddExpenseViewModel = hiltViewModel()
    val savingsViewModel: AddSavingsViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember {
        mutableStateOf(0)
    }

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

//    Scaffold(
//        modifier = Modifier.padding(top = 24.dp),
//        topBar = {
//            TabRow(
//                selectedTabIndex = selectedTab,
//                containerColor = MaterialTheme.colorScheme.surface,
//                contentColor = MaterialTheme.colorScheme.primary,
//            ) {
//                Tab(
//                    selected = selectedTab == 0,
//                    onClick = { selectedTab = 0 },
//                    selectedContentColor = MaterialTheme.colorScheme.onSurface,
//                    unselectedContentColor = Color.Gray,
//                ) {
//                    Text(
//                        text = "Add Transaction",
//                    )
//                }
//
//                Tab(
//                    selected = selectedTab == 1,
//                    onClick = { selectedTab = 1 },
//                    selectedContentColor = MaterialTheme.colorScheme.onSurface,
//                    unselectedContentColor = Color.Gray,
//                ) {
//                    Text(
//                        text = "Add Savings",
//                    )
//                }
//            }
//        }
//    ) { paddingValues ->
//        when (selectedTab) {
//            0 -> {
//                DataForm(
//                    modifier = Modifier.padding(paddingValues),
//                    onAddExpenseClick = { expense ->
//                        coroutineScope.launch {
//                            if (viewModel.addExpense(expense)) {
//                                navController.popBackStack()
//                            }
//                        }
//                    },
//                    viewModel = viewModel,
//                    navController = navController
//                )
//            }
//
//            1 -> {
//                SavingsForm(
//                    modifier = Modifier.padding(paddingValues),
//                    onAddSavingClick = { saving ->
//                        coroutineScope.launch {
//                            if (savingsViewModel.addSaving(saving)) {
//                                navController.popBackStack()
//                            }
//                        }
//                    },
//                    viewModel = savingsViewModel,
//                    navController = navController
//                )
//            }
//        }
//    }
}