package io.github.ajiekcx.declarativeSnackbar.ui.screen.localSnackbar

class SnackbarWithAction(
    val message: String,
    val actionTitle: String,
    val onActionClick: () -> Unit
)