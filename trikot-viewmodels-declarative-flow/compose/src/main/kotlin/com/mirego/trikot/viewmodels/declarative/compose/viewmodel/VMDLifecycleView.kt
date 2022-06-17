package com.mirego.trikot.viewmodels.declarative.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.mirego.trikot.viewmodels.declarative.viewmodel.VMDLifecycleViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun VMDLifecycleView(
    viewModel: VMDLifecycleViewModel,
    preOnAppear: suspend (CoroutineScope) -> Unit = {},
    content: @Composable () -> Unit
) {
    content()
    LaunchedEffect(LocalLifecycleOwner.current) {
        preOnAppear(this)
        viewModel.onAppear()
    }
    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            viewModel.onDisappear()
        }
    }
}
