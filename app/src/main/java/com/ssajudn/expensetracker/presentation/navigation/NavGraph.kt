package com.ssajudn.expensetracker.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssajudn.expensetracker.presentation.add_screen.AddExpense
import com.ssajudn.expensetracker.presentation.balance_screen.BalanceScreen
import com.ssajudn.expensetracker.presentation.home_screen.HomeScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavGraphSetup(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.AddExpense.route) {
            AddExpense(navController = navController)
        }

        composable(Routes.BalanceScreen.route) {
            BalanceScreen()
        }
    }
}