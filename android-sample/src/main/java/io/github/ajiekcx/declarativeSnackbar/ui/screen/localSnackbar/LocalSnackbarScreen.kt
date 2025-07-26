package io.github.ajiekcx.declarativeSnackbar.ui.screen.localSnackbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.ajiekcx.declarativeSnackbar.ui.components.ButtonsContainer
import io.github.ajiekcx.declarativeSnackbar.ui.SnackbarBox
import io.github.ajiekcx.declarativeSnackbar.ui.noOverlapTopContentBySnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalSnackbarScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LocalSnackbarViewModel = viewModel()
) {
    SnackbarBox(
        component = viewModel.snackbarComponent,
        alignment = Alignment.TopCenter,
        snackbarContent = { message ->
            Snackbar(
                Modifier.padding(horizontal = 16.dp),
                action = {
                    TextButton(onClick = { message.onActionClick() }) {
                        Text(message.actionTitle)
                    }
                }
            ) {
                Text(message.message)
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Local Snackbar")
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.noOverlapTopContentBySnackbar()
                )
            },
            modifier = modifier
        ) { contentPadding ->
            ButtonsContainer(Modifier.padding(contentPadding)) {
                Button(
                    onClick = viewModel::onShowGlobalSnackbarClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show global message")
                }
                Button(
                    onClick = viewModel::onShowTopSnackbarWithActionClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show local message")
                }
            }
        }
    }
}