package io.github.ajiekcx.declarativeSnackbar.ui.screen.custom

class BannerMessage(
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit,
    val onCloseClick: () -> Unit
)