package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarDuration
import com.mirego.trikot.viewmodels.declarative.components.VMDSnackbarViewData
import kotlinx.coroutines.flow.Flow

@Composable
fun VMDSnackbarFlow(
    snackbarFlow: Flow<VMDSnackbarViewData>,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(snackbarFlow) {
        snackbarFlow.collect { data ->
            snackbarHostState.showSnackbar(
                message = data.message,
                duration = data.duration.toComposeDuration(),
                withDismissAction = data.withDismissAction
            )
        }
    }
}

private fun VMDSnackbarDuration.toComposeDuration() = when (this) {
    VMDSnackbarDuration.SHORT -> SnackbarDuration.Short
    VMDSnackbarDuration.LONG -> SnackbarDuration.Long
    VMDSnackbarDuration.INDEFINITE -> SnackbarDuration.Indefinite
}
