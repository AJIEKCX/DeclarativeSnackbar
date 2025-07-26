package io.github.ajiekcx.declarativeSnackbar.core

import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Interface that manages the state and lifecycle of snackbar messages.
 *
 * The [SnackbarComponent] is responsible for showing and hiding snackbar messages,
 * managing their state, and handling their lifecycle. It provides a reactive state
 * that can be observed to render the appropriate UI based on the current state.
 *
 * @param T The type of content to be displayed in the snackbar.
 * @property state A [StateFlow] representing the current state of the snackbar.
 * @property animationDuration The duration of the show/hide animation for the snackbar.
 */
public interface SnackbarComponent<T> {
    /**
     * A [StateFlow] representing the current state of the snackbar.
     * This can be collected to reactively update the UI based on state changes.
     */
    public val state: StateFlow<SnackbarState<T>>

    /**
     * The duration of the show/hide animation for the snackbar.
     * This is used to configure the animation when the snackbar appears or disappears.
     */
    public val animationDuration: Duration

    /**
     * Shows a snackbar message with the specified content and duration.
     *
     * If a message is already being displayed, it will be replaced with the new message.
     *
     * @param message The [SnackbarContent] containing the content and duration to be displayed.
     */
    public fun show(message: SnackbarContent<T>)

    /**
     * Hides the currently displayed snackbar message.
     *
     * If no message is currently displayed, this method has no effect.
     */
    public fun hide()

    /**
     * Cleans up resources used by this component.
     *
     * This should be called when the component is no longer needed to prevent memory leaks.
     */
    public fun onDestroy()
}

/**
 * Creates an instance of [SnackbarComponent] with the specified configuration.
 *
 * This factory function provides a convenient way to create a [SnackbarComponent] instance
 * without directly instantiating the implementation class. It returns a default implementation
 * of [SnackbarComponent] configured with the provided parameters.
 *
 * Example usage:
 * ```
 * val snackbarComponent = SnackbarComponent<String>(animationDuration = 300.milliseconds)
 * ```
 *
 * @param T The type of content to be displayed in the snackbar.
 * @param animationDuration The duration of the animation used when showing or hiding messages.
 * Defaults to 500 milliseconds.
 * @return An instance of [SnackbarComponent] with the specified configuration.
 */
public fun <T> SnackbarComponent(
    animationDuration: Duration = 500.milliseconds,
): SnackbarComponent<T> {
    return DefaultSnackbarComponent(
        animationDuration = animationDuration,
    )
}
