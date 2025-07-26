package io.github.ajiekcx.declarativeSnackbar.ui.screen.nested

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.ajiekcx.declarativeSnackbar.GlobalSnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarDuration
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarMessage
import io.github.ajiekcx.declarativeSnackbar.ui.noOverlapBottomContentBySnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedNavigationScreen(
    modifier: Modifier = Modifier,
    onNavigateLocalSnackbarClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    GlobalSnackbarComponent.show(
                        SnackbarMessage(
                            duration = SnackbarDuration.Short,
                            content = "Add clicked"
                        )
                    )
                },
                modifier = Modifier.noOverlapBottomContentBySnackbar()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onNavigateLocalSnackbarClick
            ) {
                Text("Open local snackbar screen")
            }
        }
    }
}