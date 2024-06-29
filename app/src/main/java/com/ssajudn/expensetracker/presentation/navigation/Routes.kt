package com.ssajudn.expensetracker.presentation.navigation


sealed class Routes(val route: String) {
    data object HomeScreen : Routes("home_screen")

    data object AddExpense : Routes("add_expense")

    data object BalanceScreen : Routes("balance_screen")
}