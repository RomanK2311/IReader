package org.ireader.core_ui.theme

import androidx.compose.runtime.*

@Composable
fun TransparentStatusBar(content: @Composable () -> Unit) {
    val state = LocalTransparentStatusBar.current
    DisposableEffect(Unit) {
        state.enabled = true
        onDispose {
            state.enabled = false
        }
    }
    content()
}

val LocalTransparentStatusBar = staticCompositionLocalOf { TransparentStatusBar(false) }

class TransparentStatusBar(enabled: Boolean) {
    var enabled by mutableStateOf(enabled)
}