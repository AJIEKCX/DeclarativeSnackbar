package io.github.ajiekcx.declarativeSnackbar.core

/**
 * Sealed class that represents the state of a snackbar.
 *
 * This class is used to model the different states a snackbar can be in:
 * - [Hidden]: The snackbar is not currently visible
 * - [Shown]: The snackbar is currently visible and displaying a message
 *
 * The state includes an [isVisible] property that can be used to determine if the snackbar
 * should be rendered in the UI.
 *
 * @param T The type of content displayed in the snackbar.
 */
public sealed class SnackbarState<T> {
    /**
     * Indicates whether the snackbar is currently visible.
     *
     * This property can be used to control the visibility of the snackbar in the UI.
     * - `true` if the snackbar is visible ([Shown])
     * - `false` if the snackbar is hidden ([Hidden])
     */
    public abstract val isVisible: Boolean

    /**
     * Data class representing the state when the snackbar is hidden (not visible).
     *
     * This state is used when no message is currently being displayed, but it may contain
     * a reference to the previously displayed message content. This can be useful for
     * animation purposes or for restoring the previous message if needed.
     *
     * @param T The type of content that was displayed in the snackbar.
     * @property previousMessageContent The content of the message that was previously displayed,
     *                                 or null if no message has been displayed yet.
     */
    public data class Hidden<T>(
        public val previousMessageContent: SnackbarContent<T>?,
    ) : SnackbarState<T>() {
        /**
         * Always returns false since this state represents a hidden snackbar.
         */
        public override val isVisible: Boolean = false
    }

    /**
     * Data class representing the state when the snackbar is shown (visible).
     *
     * This state is used when a message is currently being displayed. It contains
     * the content of the message that is being shown.
     *
     * @param T The type of content being displayed in the snackbar.
     * @property messageContent The content of the message that is currently being displayed.
     */
    public data class Shown<T>(
        public val messageContent: SnackbarContent<T>,
    ) : SnackbarState<T>() {
        /**
         * Always returns true since this state represents a visible snackbar.
         */
        public override val isVisible: Boolean = true
    }
}
