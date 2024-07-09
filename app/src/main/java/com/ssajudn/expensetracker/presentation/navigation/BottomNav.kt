package com.ssajudn.expensetracker.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            selected = currentDestination?.route == Routes.HomeScreen.route,
            onClick = {
                navController.navigate(Routes.HomeScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            label = {
                Text(
                    text = "Home",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.primary,
                selectedIconColor = MaterialTheme.colorScheme.primary
            )
        )

        NavigationBarItem(
            selected = currentDestination?.route == Routes.HistoryScreen.route,
            onClick = {
                navController.navigate(Routes.HistoryScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            label = {
                Text(
                    text = "History",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.primary,
                selectedIconColor = MaterialTheme.colorScheme.primary
            )
        )

        NavigationBarItem(
            selected = currentDestination?.route == Routes.ChatScreen.route,
            onClick = {
                navController.navigate(Routes.ChatScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            label = {
                Text(
                    text = "Gemini ✨",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.primary,
                selectedIconColor = MaterialTheme.colorScheme.primary
            )
        )

        NavigationBarItem(
            selected = currentDestination?.route == Routes.BalanceScreen.route,
            onClick = {
                navController.navigate(Routes.BalanceScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.CreditCard,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            label = {
                Text(
                    text = "Balance",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.primary,
                selectedIconColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}
