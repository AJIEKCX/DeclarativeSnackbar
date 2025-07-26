package io.github.ajiekcx.declarativeSnackbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.ajiekcx.declarativeSnackbar.ui.screen.bottomNavigation.BottomNavigationScreen
import io.github.ajiekcx.declarativeSnackbar.ui.screen.localSnackbar.LocalSnackbarScreen
import io.github.ajiekcx.declarativeSnackbar.ui.theme.DeclarativeSnackbarTheme
import io.github.ajiekcx.declarativeSnackbar.ui.SnackbarBox
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeclarativeSnackbarTheme {
                NavGraph()
            }
        }
    }
}

@Serializable
object BottomNavigationScreen

@Serializable
object LocalSnackbarScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    SnackbarBox(
        component = GlobalSnackbarComponent,
        alignment = Alignment.BottomCenter,
        snackbarContent = { message ->
            Snackbar(
                Modifier
                    .padding(horizontal = 16.dp)
                    .navigationBarsPadding()
            ) {
                Text(message)
            }
        },
    ) {
        NavHost(navController = navController, startDestination = BottomNavigationScreen) {
            composable<BottomNavigationScreen> {
                BottomNavigationScreen(
                    onNavigateLocalSnackbarClick = {
                        navController.navigate(LocalSnackbarScreen)
                    }
                )
            }
            composable<LocalSnackbarScreen> {
                LocalSnackbarScreen(
                    onBackClick = navController::popBackStack
                )
            }
        }
    }
}


