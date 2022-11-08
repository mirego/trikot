package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDLifecycleViewModel

/**
 * For views backed by a VMDLifecycleViewModel, wrap your content with VMDLifecycleView to properly bind the lifecycle.
 */
@Composable
fun VMDLifecycleView(
    lifecycleViewModel: VMDLifecycleViewModel,
    content: @Composable () -> Unit
) {
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    content()
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                lifecycleViewModel.onAppear()
            } else if (event == Lifecycle.Event.ON_STOP) {
                lifecycleViewModel.onDisappear()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                lifecycleViewModel.onDisappear()
            }
        }
    }
}
