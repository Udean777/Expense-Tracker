package com.ssajudn.expensetracker.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssajudn.expensetracker.presentation.add_screen.AddExpense
import com.ssajudn.expensetracker.presentation.home_screen.HomeScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavGraphSetup(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            HomeScreen(navController = navController)
        }

        composable<Routes.AddExpense> {
            AddExpense(navController = navController)
        }
    }

}