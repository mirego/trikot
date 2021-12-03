package com.mirego.sample.ui.showcase

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.mirego.sample.viewmodels.showcase.ShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun ComponentShowcaseTopBar(viewModel: ShowcaseViewModel) {
    TopAppBar(
        title = { VMDText(viewModel = viewModel.title) },
        actions = {
            VMDButton(viewModel = viewModel.closeButton) {
                VMDImage(
                    imageDescriptor = viewModel.closeButton.content.image
                )
            }
        }
    )
}
