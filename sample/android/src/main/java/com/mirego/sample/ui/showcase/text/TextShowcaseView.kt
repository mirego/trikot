package com.mirego.sample.ui.showcase.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.theming.sampleTypography
import com.mirego.sample.viewmodels.showcase.text.TextShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun TextShowcaseView(textShowcaseViewModel: TextShowcaseViewModel) {
    val viewModel: TextShowcaseViewModel by textShowcaseViewModel.observeAsState()

    Column(modifier = Modifier.fillMaxWidth()) {

        TopAppBar(
            title = { VMDText(viewModel = viewModel.title) },
            actions = {
                VMDButton(viewModel = viewModel.closeButton) {
                    LocalImage(
                        imageResource = viewModel.closeButton.content.image
                    )
                }
            }
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.title1,
            style = sampleTypography.title1
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.title1Bold,
            style = sampleTypography.title1Bold
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.title2,
            style = sampleTypography.title2
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.title2Bold,
            style = sampleTypography.title2Bold
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.body,
            style = sampleTypography.body
        )

        VMDText(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.bodyMedium,
            style = sampleTypography.bodyMedium
        )
    }
}
