package com.mirego.sample.ui.showcase.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative

@Composable
fun ButtonShowcaseView(buttonShowcaseViewModel: ButtonShowcaseViewModel) {
    val viewModel: ButtonShowcaseViewModel by buttonShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        ComponentShowcaseTopBar(viewModel)

        ComponentShowcaseTitle(viewModel.textButtonTitle)

        VMDButton(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.textButton
        ) { content ->
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                text = content.text,
                style = SampleTextStyle.body.medium(),
                color = MaterialTheme.colors.onPrimary
            )
        }

        ComponentShowcaseTitle(viewModel.imageButtonTitle)

        VMDButton(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.imageButton
        ) { content ->
            LocalImage(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                    .padding(4.dp),
                imageResource = content.image
            )
        }

        ComponentShowcaseTitle(viewModel.textImageButtonTitle)

        VMDButton(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.textImageButton
        ) { content ->
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocalImage(
                    modifier = Modifier.padding(end = 8.dp),
                    imageResource = content.image
                )
                Text(
                    text = content.text,
                    style = SampleTextStyle.body.medium(),
                    color = MaterialTheme.colors.onPrimary
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
                    .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = content.first,
                    style = SampleTextStyle.title2,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = content.second,
                    style = SampleTextStyle.body,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ButtonShowcaseViewPreview() {
    TrikotViewModelDeclarative.initialize(SampleImageProvider())
    ButtonShowcaseView(buttonShowcaseViewModel = ButtonShowcaseViewModelPreview())
}
