package com.ssajudn.expensetracker.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreen : Routes() {
        override val route: String = "home_screen"
    }

    @Serializable
    data object AddExpense : Routes() {
        override val route: String = "add_expense"
    }

    @Serializable
    data object BalanceScreen : Routes() {
        override val route: String = "balance_screen"
    }

    abstract val route: String
}