package io.github.ajiekcx.declarativeSnackbar.ui.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.ajiekcx.declarativeSnackbar.ui.components.ButtonsContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel()
) {
    ButtonsContainer(modifier) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::onShowShortMessageClicked
        ) {
            Text(text = "Show short duration message")
        }
        Button(
            onClick = viewModel::onShowPermanentMessageClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Show permanent message")
        }
        Button(
            onClick = viewModel::onHideClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Hide message")
        }
    }
}