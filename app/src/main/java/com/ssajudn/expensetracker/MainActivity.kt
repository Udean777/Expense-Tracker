package com.ssajudn.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssajudn.expensetracker.presentation.navigation.BottomNavBar
import com.ssajudn.expensetracker.presentation.navigation.NavGraphSetup
import com.ssajudn.expensetracker.presentation.navigation.Routes
import com.ssajudn.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    bottomBar = {
                        if (!navController.currentBackStackEntry?.destination?.route.equals(Routes.AddExpense.route)) {
                            BottomNavBar(navController = navController)
                        }
                    },
                    topBar = {
                        TopAppBar(
                            title = {

                            },
                            navigationIcon = {
                                if (currentDestination?.route == Routes.AddExpense.route) {
                                    IconButton(
                                        onClick = {
                                            navController.popBackStack()
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Menu",
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Menu",
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            },
                            actions = {
                                if (currentDestination?.route == Routes.AddExpense.route) {
                                    null
                                } else {
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Routes.AddExpense.route)
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Add,
                                            contentDescription = "Menu",
                                            modifier = Modifier.padding(end = 8.dp)
                                        )
                                    }
                                }
                            }
                        )
                    }
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
