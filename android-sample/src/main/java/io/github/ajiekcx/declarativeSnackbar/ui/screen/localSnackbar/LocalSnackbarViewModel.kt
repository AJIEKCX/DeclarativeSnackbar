package io.github.ajiekcx.declarativeSnackbar.ui.screen.localSnackbar

import androidx.lifecycle.ViewModel
import io.github.ajiekcx.declarativeSnackbar.GlobalSnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarComponent
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarDuration
import io.github.ajiekcx.declarativeSnackbar.core.SnackbarMessage

class LocalSnackbarViewModel : ViewModel() {
    val snackbarComponent: SnackbarComponent<SnackbarWithAction> = SnackbarComponent()

    fun onShowGlobalSnackbarClick() {
        GlobalSnackbarComponent.show(
            SnackbarMessage(
                content = "This is a global message",
                duration = SnackbarDuration.Short
            )
        )
    }

    fun onShowTopSnackbarWithActionClick() {
        snackbarComponent.show(
            SnackbarMessage(
                duration = SnackbarDuration.Permanent,
                content = SnackbarWithAction(
                    message = "Close me",
                    actionTitle = "Close",
                    onActionClick = { snackbarComponent.hide() }
                ),
            )
        )
    }

    override fun onCleared() {
        super.onCleared()

        snackbarComponent.onDestroy()
    }
}