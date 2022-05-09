package org.ireader.components.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun ISnackBarHost(snackBarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackBarHostState) { data ->
        Snackbar(
            actionColor = MaterialTheme.colors.primary,
            snackbarData = data,
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
        )
    }
}