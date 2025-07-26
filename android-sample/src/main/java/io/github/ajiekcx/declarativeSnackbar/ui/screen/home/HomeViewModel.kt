package io.github.ajiekcx.declarativeSnackbar.ui.screen.home

import androidx.lifecycle.ViewModel
import io.github.ajiekcx.declarativeSnackbar.GlobalSnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarDuration
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarMessage

class HomeViewModel : ViewModel() {
    fun onShowShortMessageClicked() {
        GlobalSnackbarComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Short,
                content = "This is a short message",
            )
        )
    }

    fun onShowPermanentMessageClicked() {
        GlobalSnackbarComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Permanent,
                content = "This is a permanent message",
            )
        )
    }

    fun onHideClicked() {
        GlobalSnackbarComponent.hide()
    }
}