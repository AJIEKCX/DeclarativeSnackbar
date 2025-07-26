package io.github.ajiekcx.declarativeSnackbar.ui.screen.custom

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.ajiekcx.declarativeSnackbar.ui.components.ButtonsContainer
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.ui.SnackbarBox

@Composable
fun CustomSnackbarScreen(viewModel: CustomSnackbarViewModel = viewModel()) {
    TopSnackbarBox(viewModel.snackbarTopComponent) {
        BottomSnackbarBox(viewModel.snackbarBottomComponent) {
            ButtonsContainer {
                Button(
                    onClick = viewModel::onShowTopSnackbarClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show top snackbar")
                }
                Button(
                    onClick = viewModel::onShowBottomSnackbarClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show bottom snackbar")
                }
            }
        }
    }
}

@Composable
private fun TopSnackbarBox(
    component: SnackbarComponent<String>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    SnackbarBox(
        component = component,
        snackbarContent = { message ->
            TopSnackbarContent(message)
        },
        alignment = Alignment.TopCenter,
        modifier = modifier.fillMaxSize(),
        content = content
    )
}

@Composable
private fun TopSnackbarContent(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.onError
        )
    }

}

@Composable
private fun BottomSnackbarBox(
    component: SnackbarComponent<BannerMessage>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val bounceEnterTransition = slideInVertically(
        initialOffsetY = { it },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val fadeExitTransition = fadeOut()
    SnackbarBox(
        component = component,
        snackbarContent = { message ->
            BottomBannerContent(message)
        },
        alignment = Alignment.BottomCenter,
        animationEnterTransition = bounceEnterTransition,
        animationExitTransition = fadeExitTransition,
        modifier = modifier.fillMaxSize(),
        content = content
    )
}

@Composable
private fun BottomBannerContent(
    message: BannerMessage,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.large,
        colors = cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            modifier = Modifier
                .clickable { message.onClick() }
                .fillMaxWidth()
        ) {
            Row {
                Column(
                    Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = message.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = message.subtitle,
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                IconButton(onClick = message.onCloseClick) {
                    Icon(Icons.Default.Close, contentDescription = "close")
                }
            }
        }
    }
}