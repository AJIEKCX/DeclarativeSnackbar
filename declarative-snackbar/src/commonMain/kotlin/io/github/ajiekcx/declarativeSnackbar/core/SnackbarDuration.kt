package io.github.ajiekcx.declarativeSnackbar.core

/**
 * Sealed interface representing different durations for a message.
 * This interface is used to define how long a message should be displayed.
 */
public sealed interface SnackbarDuration {
    /**
     * The duration in milliseconds for which the message should be displayed.
     */
    public val timeInMillis: Long

    /**
     * Represents a short duration for the message display.
     * The message will be displayed for 3000 milliseconds (3 seconds).
     */
    public data object Short : SnackbarDuration {
        override val timeInMillis: Long = 3000
    }

    /**
     * Represents a permanent duration for the message display.
     * The message will be displayed indefinitely until manually dismissed.
     */
    public data object Permanent : SnackbarDuration {
        override val timeInMillis: Long = Long.MAX_VALUE
    }

    /**
     * Represents a custom duration for the message display.
     * The duration is specified by the user and can be any positive long value.
     *
     * @property timeInMillis The duration in milliseconds for which the message should be displayed.
     */
    public data class Custom(
        override val timeInMillis: Long,
    ) : SnackbarDuration
}
