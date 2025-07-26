package io.github.ajiekcx.declarativeSnackbar.ui.screen.bottomNavigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.ajiekcx.declarativeSnackbar.ui.components.BottomNavBar
import io.github.ajiekcx.declarativeSnackbar.ui.components.BottomNavItem
import io.github.ajiekcx.declarativeSnackbar.ui.screen.home.HomeScreen
import io.github.ajiekcx.declarativeSnackbar.ui.screen.nested.NestedNavigationScreen
import io.github.ajiekcx.declarativeSnackbar.ui.screen.custom.CustomSnackbarScreen
import io.github.ajiekcx.declarativeSnackbar.ui.noOverlapBottomContentBySnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationScreen(
    onNavigateLocalSnackbarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                navController = navController,
                modifier = Modifier.noOverlapBottomContentBySnackbar()
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen()
            }
            composable(BottomNavItem.Info.route) {
                NestedNavigationScreen(onNavigateLocalSnackbarClick = onNavigateLocalSnackbarClick)
            }
            composable(BottomNavItem.Custom.route) {
                CustomSnackbarScreen()
            }
        }
    }
}
