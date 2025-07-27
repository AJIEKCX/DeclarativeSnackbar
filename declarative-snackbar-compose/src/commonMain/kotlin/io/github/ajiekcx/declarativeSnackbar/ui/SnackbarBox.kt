package io.github.ajiekcx.declarativeSnackbar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarState

/**
 * Composable function to display a messenger box with customizable content and animations. This function uses a [SnackbarComponent]
 * to manage the state and display of messages.
 *
 * @param component the [SnackbarComponent] responsible for managing and displaying messages.
 * @param snackbarContent the composable content for display message.
 * @param modifier the modifier to apply to the messenger box.
 * @param alignment the alignment of the messenger box within its parent.
 * @param animationEnterTransition the enter transition animation for the messenger box.
 * @param animationExitTransition the exit transition animation for the messenger box.
 * @param content the composable content to be displayed within the messenger box.
 */
@Composable
public fun <T> SnackbarBox(
    component: SnackbarComponent<T>,
    snackbarContent: @Composable (content: T) -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.BottomCenter,
    animationEnterTransition: EnterTransition = SnackbarBoxDefaults.enterTransition(
        alignment = alignment,
        animationDuration = component.animationDuration
    ),
    animationExitTransition: ExitTransition = SnackbarBoxDefaults.exitTransition(
        alignment = alignment,
        animationDuration = component.animationDuration
    ),
    content: @Composable () -> Unit,
) {
    val componentState by component.state.collectAsState()
    val bottomMessengerOffset = remember { mutableStateMapOf<String, Int>() }
    val topMessengerOffset = remember { mutableStateMapOf<String, Int>() }
    CompositionLocalProvider(
        LocalBottomMessengerOffset provides bottomMessengerOffset,
        LocalTopMessengerOffset provides topMessengerOffset,
    ) {
        Box(modifier = modifier) {
            content()
            AnimatedVisibility(
                modifier = Modifier
                    .align(alignment)
                    .fillMaxWidth(),
                visible = componentState.isVisible,
                enter = animationEnterTransition,
                exit = animationExitTransition,
            ) {
                val message = when (val state = componentState) {
                    is SnackbarState.Hidden -> state.previousMessageContent
                    is SnackbarState.Shown -> state.messageContent
                }
                val messageContent = message?.content
                if (messageContent != null) {
                    val additionalPadding = SnackbarBoxDefaults.animateAdditionalPadding(alignment)
                    Box(Modifier.padding(additionalPadding)) {
                        snackbarContent(messageContent)
                    }
                }
            }
        }
    }
}
