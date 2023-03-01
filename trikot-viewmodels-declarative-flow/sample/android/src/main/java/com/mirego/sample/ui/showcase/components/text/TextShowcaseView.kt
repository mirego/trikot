package com.mirego.sample.ui.showcase.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.bold
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative

@Composable
fun TextShowcaseView(textShowcaseViewModel: TextShowcaseViewModel) {
    val viewModel: TextShowcaseViewModel by textShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {
        ComponentShowcaseTopBar(viewModel)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            VMDText(
                viewModel = viewModel.largeTitle,
                style = SampleTextStyle.largeTitle
            )

            VMDText(
                viewModel = viewModel.title1,
                style = SampleTextStyle.title1
            )

            VMDText(
                viewModel = viewModel.title1Bold,
                style = SampleTextStyle.title1.bold()
            )

            VMDText(
                viewModel = viewModel.title2,
                style = SampleTextStyle.title2
            )

            VMDText(
                viewModel = viewModel.title2Bold,
                style = SampleTextStyle.title2.bold()
            )

            VMDText(
                viewModel = viewModel.title3,
                style = SampleTextStyle.title3
            )

            VMDText(
                viewModel = viewModel.headline,
                style = SampleTextStyle.headline
            )

            VMDText(
                viewModel = viewModel.body,
                style = SampleTextStyle.body
            )

            VMDText(
                viewModel = viewModel.bodyMedium,
                style = SampleTextStyle.body.medium()
            )

            VMDText(
                viewModel = viewModel.button,
                style = SampleTextStyle.button
            )

            VMDText(
                viewModel = viewModel.callout,
                style = SampleTextStyle.callout
            )

            VMDText(
                viewModel = viewModel.subheadline,
                style = SampleTextStyle.subheadline
            )

            VMDText(
                viewModel = viewModel.footnote,
                style = SampleTextStyle.footnote
            )

            VMDText(
                viewModel = viewModel.caption1,
                style = SampleTextStyle.caption1
            )

            VMDText(
                viewModel = viewModel.caption2,
                style = SampleTextStyle.caption2
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TextShowcaseViewPreview() {
    TrikotViewModelDeclarative.initialize(SampleImageProvider())
    TextShowcaseView(textShowcaseViewModel = TextShowcaseViewModelPreview())
}
