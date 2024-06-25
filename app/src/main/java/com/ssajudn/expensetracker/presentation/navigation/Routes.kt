package com.ssajudn.expensetracker.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data object AddExpense: Routes()
}