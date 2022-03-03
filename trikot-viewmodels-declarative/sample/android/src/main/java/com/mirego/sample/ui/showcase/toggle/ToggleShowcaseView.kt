package com.mirego.sample.ui.showcase.toggle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.viewmodels.showcase.toggle.ToggleShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.toggle.ToggleShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDCheckbox
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDSwitch
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative

@Composable
fun ToggleShowcaseView(toggleShowcaseViewModel: ToggleShowcaseViewModel) {
    val viewModel: ToggleShowcaseViewModel by toggleShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        ComponentShowcaseTopBar(viewModel)

        ComponentShowcaseTitle(viewModel.checkboxTitle)

        VMDCheckbox(
            modifier = Modifier
                .padding(start = 2.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.emptyToggle
        )

        ComponentShowcaseTitle(viewModel.textCheckboxTitle)

        VMDCheckbox(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.textToggle,
            label = { Text(it.text, style = SampleTextStyle.body) }
        )

        ComponentShowcaseTitle(viewModel.imageCheckboxTitle)

        VMDCheckbox(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.imageToggle,
            label = { content ->
                LocalImage(
                    imageResource = content.image,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
        )

        ComponentShowcaseTitle(viewModel.textImageCheckboxTitle)

        VMDCheckbox(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.textImageToggle,
            label = { content ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LocalImage(
                        imageResource = content.image,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body
                    )
                }
            }
        )

        ComponentShowcaseTitle(viewModel.textPairCheckboxTitle)

        VMDCheckbox(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.textPairToggle,
            label = { content ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = content.first,
                        style = SampleTextStyle.body
                    )
                    Text(
                        text = content.second,
                        style = SampleTextStyle.caption1
                    )
                }
            }
        )

        ComponentShowcaseTitle(viewModel.switchTitle)

        VMDSwitch(
            modifier = Modifier.padding(start = 10.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.emptyToggle
        )

        ComponentShowcaseTitle(viewModel.textSwitchTitle)

        VMDSwitch(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.textToggle,
            label = { Text(it.text, style = SampleTextStyle.body) }
        )

        ComponentShowcaseTitle(viewModel.imageSwitchTitle)

        VMDSwitch(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.imageToggle,
            label = { content ->
                LocalImage(
                    imageResource = content.image,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
        )

        ComponentShowcaseTitle(viewModel.textImageSwitchTitle)

        VMDSwitch(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            viewModel = viewModel.textImageToggle,
            label = { content ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LocalImage(
                        imageResource = content.image,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = content.text,
                        style = SampleTextStyle.body
                    )
                }
            }
        )

        ComponentShowcaseTitle(viewModel.textPairSwitchTitle)

        VMDSwitch(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            viewModel = viewModel.textPairToggle,
            label = { content ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = content.first,
                        style = SampleTextStyle.body
                    )
                    Text(
                        text = content.second,
                        style = SampleTextStyle.caption1
                    )
                }
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ToggleShowcaseViewPreview() {
    TrikotViewModelDeclarative.initialize(SampleImageProvider())
    ToggleShowcaseView(toggleShowcaseViewModel = ToggleShowcaseViewModelPreview())
}
