package com.mirego.sample.ui.showcase.button

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
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.sampleTypography
import com.mirego.sample.viewmodels.showcase.button.ButtonShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDImage

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
                style = sampleTypography.bodyMedium,
                color = MaterialTheme.colors.onPrimary
            )
        }

        ComponentShowcaseTitle(viewModel.imageButtonTitle)

        VMDButton(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            viewModel = viewModel.imageButton
        ) { content ->
            VMDImage(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                    .padding(4.dp),
                imageDescriptor = content.image
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
                VMDImage(
                    modifier = Modifier.padding(end = 8.dp),
                    imageDescriptor = content.image
                )
                Text(
                    text = content.text,
                    style = sampleTypography.bodyMedium,
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
                    style = sampleTypography.title2,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = content.second,
                    style = sampleTypography.body,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}
