package io.github.ajiekcx.declarativeSnackbar.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration

internal class DefaultSnackbarComponent<T>(
    override val animationDuration: Duration,
) : SnackbarComponent<T> {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var showingJob: Job? = null
    private var dismissingJob: Job? = null
    private val _state = MutableStateFlow<SnackbarState<T>>(
        SnackbarState.Hidden(previousMessageContent = null)
    )
    override val state: StateFlow<SnackbarState<T>> = _state.asStateFlow()

    override fun show(message: SnackbarContent<T>) {
        when (val currentState = state.value) {
            is SnackbarState.Hidden<T> -> {
                showMessage(message)
            }

            is SnackbarState.Shown<T> -> {
                dismissMessageAndShow(
                    previousMessageContent = currentState.messageContent,
                    message = message
                )
            }
        }
    }

    override fun hide() {
        when (val currentState = state.value) {
            is SnackbarState.Hidden<T> -> Unit
            is SnackbarState.Shown<T> -> {
                _state.value = SnackbarState.Hidden(
                    previousMessageContent = currentState.messageContent
                )
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }

    private fun dismissMessageAndShow(
        previousMessageContent: SnackbarContent<T>?,
        message: SnackbarContent<T>
    ) {
        if (dismissingJob?.isActive == true) return

        dismissingJob = coroutineScope.launch {
            _state.value = SnackbarState.Hidden(
                previousMessageContent = previousMessageContent
            )
            delay(animationDuration)
            showMessage(message = message)
        }
    }

    private fun showMessage(message: SnackbarContent<T>) {
        dismissingJob?.cancel()
        showingJob?.cancel()
        showingJob = coroutineScope.launch {
            val appliedState = SnackbarState.Shown(message)
            _state.value = appliedState
            delay(message.duration.timeInMillis)
            _state.value = SnackbarState.Hidden(
                previousMessageContent = appliedState.messageContent
            )
        }
    }
}