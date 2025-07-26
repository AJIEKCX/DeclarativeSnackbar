package io.github.ajiekcx.declarativeSnackbar.ui.screen.custom

import androidx.lifecycle.ViewModel
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarDuration
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarMessage

class CustomSnackbarViewModel : ViewModel() {
    val snackbarTopComponent: SnackbarComponent<String> = SnackbarComponent()
    val snackbarBottomComponent: SnackbarComponent<BannerMessage> = SnackbarComponent()

    fun onShowTopSnackbarClicked() {
        snackbarTopComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Short,
                content = "No internet connection"
            )
        )
    }

    fun onShowBottomSnackbarClicked() {
        snackbarBottomComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Permanent,
                content = BannerMessage(
                    title = "An app update is available",
                    subtitle = "Click here to update.",
                    onClick = snackbarBottomComponent::hide,
                    onCloseClick = snackbarBottomComponent::hide
                )
            )
        )
    }

    override fun onCleared() {
        super.onCleared()

        snackbarTopComponent.onDestroy()
        snackbarBottomComponent.onDestroy()
    }
}