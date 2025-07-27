package io.github.ajiekcx.declarativeSnackbar.core

/**
 * A concrete implementation of [SnackbarContent] that represents a message to be displayed in a snackbar.
 *
 * @param T The type of content to be displayed in the snackbar.
 * @property duration The duration for which the snackbar should be displayed.
 * @property content The actual content to be displayed in the snackbar.
 */
public class SnackbarMessage<T>(
    public override val duration: SnackbarDuration,
    public override val content: T,
) : SnackbarContent<T>
