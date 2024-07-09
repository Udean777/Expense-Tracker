package com.ssajudn.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssajudn.expensetracker.presentation.navigation.BottomNavBar
import com.ssajudn.expensetracker.presentation.navigation.NavGraphSetup
import com.ssajudn.expensetracker.presentation.navigation.Routes
import com.ssajudn.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val isAddExpenseRoute = currentRoute == Routes.AddExpense.route

                Scaffold(
                    bottomBar = {
                        if (!isAddExpenseRoute) {
                            BottomNavBar(navController = navController)
                        }
                    },
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
                        NavGraphSetup(navController = navController)
                    }
                }
            }
        }
    }
}
