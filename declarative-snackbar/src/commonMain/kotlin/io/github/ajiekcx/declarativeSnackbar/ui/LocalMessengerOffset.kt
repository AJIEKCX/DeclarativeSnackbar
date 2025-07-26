package io.github.ajiekcx.declarativeSnackbar.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * A modifier that prevents content from being overlapped by a snackbar positioned at the top of the screen.
 * 
 * When applied to a composable, this modifier will automatically adjust the padding to ensure that
 * the content remains visible when a snackbar is displayed at the top of the screen.
 *
 * @return A [Modifier] with the no-overlap behavior applied.
 */
public fun Modifier.noOverlapTopContentBySnackbar(): Modifier {
    return this then LocalMessengerOffsetElement(
        alignment = ContentAlignment.Top
    )
}

/**
 * A modifier that prevents content from being overlapped by a snackbar positioned at the bottom of the screen.
 * 
 * When applied to a composable, this modifier will automatically adjust the padding to ensure that
 * the content remains visible when a snackbar is displayed at the bottom of the screen.
 *
 * @return A [Modifier] with the no-overlap behavior applied.
 */
public fun Modifier.noOverlapBottomContentBySnackbar(): Modifier {
    return this then LocalMessengerOffsetElement(
        alignment = ContentAlignment.Bottom
    )
}

@Suppress("CompositionLocalAllowlist")
internal val LocalBottomMessengerOffset = compositionLocalOf { mutableStateMapOf<String, Int>() }

@Suppress("CompositionLocalAllowlist")
internal val LocalTopMessengerOffset = compositionLocalOf { mutableStateMapOf<String, Int>() }

private data class LocalMessengerOffsetElement(
    private val alignment: ContentAlignment,
) : ModifierNodeElement<LocalMessengerOffsetModifierNode>() {
    override fun create() = LocalMessengerOffsetModifierNode(alignment)

    override fun update(node: LocalMessengerOffsetModifierNode) {}
}

private class LocalMessengerOffsetModifierNode(
    private val alignment: ContentAlignment
) : Modifier.Node(), CompositionLocalConsumerModifierNode, GlobalPositionAwareModifierNode {
    @OptIn(ExperimentalUuidApi::class)
    private val key = Uuid.random().toString()

    override fun onGloballyPositioned(coordinates: LayoutCoordinates) {
        if (coordinates.isAttached) {
            val y = coordinates.positionInWindow().y
            val rootHeight = coordinates.findRootCoordinates().size.height
            when (alignment) {
                ContentAlignment.Top -> {
                    val topOffset = (coordinates.size.height + y).toInt().coerceAtMost(rootHeight)
                    val localMessageOffsets = currentValueOf(LocalTopMessengerOffset)
                    localMessageOffsets[key] = topOffset
                }

                ContentAlignment.Bottom -> {
                    val bottomOffset = (rootHeight - y).toInt().coerceAtLeast(0)
                    val localMessageOffsets = currentValueOf(LocalBottomMessengerOffset)
                    localMessageOffsets[key] = bottomOffset
                }
            }
        }
    }

    override fun onDetach() {
        val localBottomMessageOffsets = currentValueOf(LocalBottomMessengerOffset)
        val localTopMessageOffsets = currentValueOf(LocalTopMessengerOffset)
        localBottomMessageOffsets.remove(key)
        localTopMessageOffsets.remove(key)
    }
}

private enum class ContentAlignment {
    Top,
    Bottom
}
