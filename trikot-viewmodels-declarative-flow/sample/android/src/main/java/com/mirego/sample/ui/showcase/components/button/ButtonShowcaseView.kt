package com.mirego.sample.ui.showcase.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDElevatedButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDFilledTonalButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDOutlinedButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDTextButton
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDButton as VMDMaterial3Button

@Composable
fun ButtonShowcaseView(buttonShowcaseViewModel: ButtonShowcaseViewModel) {
    val viewModel: ButtonShowcaseViewModel by buttonShowcaseViewModel.observeAsState()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            ComponentShowcaseTopBar(viewModel)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
        ) {
            ComponentShowcaseTitle(viewModel.textButtonTitle)

            VMDButton(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                viewModel = viewModel.textButton
            ) { content ->
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(6.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    text = content.text,
                    style = SampleTextStyle.body.medium(),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            ComponentShowcaseTitle(viewModel.imageButtonTitle)

            VMDButton(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                viewModel = viewModel.imageButton
            ) { content ->
                LocalImage(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(6.dp))
                        .padding(4.dp),
                    imageResource = content.image,
                    contentDescription = content.contentDescription,
                )
            }

            ComponentShowcaseTitle(viewModel.textImageButtonTitle)

            VMDButton(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                viewModel = viewModel.textImageButton
            ) { content ->
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(6.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LocalImage(
                        modifier = Modifier.padding(end = 8.dp),
                        imageResource = content.image,
                        contentDescription = content.contentDescription,
                    )
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            ComponentShowcaseTitle(viewModel.textPairButtonTitle)

            VMDButton(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                viewModel = viewModel.textPairButton
            ) { content ->
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(6.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = content.first,
                        style = SampleTextStyle.title2,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = content.second,
                        style = SampleTextStyle.body,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ComponentShowcaseTitle("Material 3 Elevated Button")

                VMDElevatedButton(viewModel.textButton) { content ->
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                VMDElevatedButton(viewModel.textImageButton) { content ->
                    LocalImage(
                        imageResource = content.image,
                        contentDescription = content.contentDescription,
                        modifier = Modifier.size(18.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                ComponentShowcaseTitle("Material 3 Filled Button")

                VMDMaterial3Button(viewModel.textButton) { content ->
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                ComponentShowcaseTitle("Material 3 Filled Tonal Button")

                VMDFilledTonalButton(viewModel.textButton) { content ->
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                ComponentShowcaseTitle("Material 3 Filled Outlined Button")

                VMDOutlinedButton(viewModel.textButton) { content ->
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                ComponentShowcaseTitle("Material 3 Filled Text Button")

                VMDTextButton(viewModel.textButton) { content ->
                    Text(
                        text = content.text,
                        style = SampleTextStyle.body.medium(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ButtonShowcaseViewPreview() {
    TrikotViewModelDeclarative.initialize(SampleImageProvider())
    ButtonShowcaseView(buttonShowcaseViewModel = ButtonShowcaseViewModelPreview())
}
