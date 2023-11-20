package com.mirego.sample.ui.tv.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.components.button.ButtonShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButtonTv
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDTextTv

@ExperimentalTvMaterial3Api
@Composable
fun ButtonTvShowcaseView(buttonShowcaseViewModel: ButtonShowcaseViewModel) {
    val viewModel: ButtonShowcaseViewModel by buttonShowcaseViewModel.observeAsState()

    TvLazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VMDButtonTv(
                    viewModel = viewModel.textButton
                ) { content, _ ->
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        text = content.text,
                        style = SampleTextStyle.body.medium()
                    )
                }
                VMDTextTv(
                    viewModel = viewModel.textButtonTitle,
                    color = Color.White
                )
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VMDButtonTv(
                    viewModel = viewModel.imageButton
                ) { content, isFocused ->
                    val contentColor = when (isFocused) {
                        true -> MaterialTheme.colorScheme.inverseOnSurface
                        false -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    }
                    LocalImage(
                        modifier = Modifier
                            .padding(4.dp),
                        imageResource = content.image,
                        contentDescription = content.contentDescription,
                        colorFilter = ColorFilter.tint(contentColor)
                    )
                }
                VMDTextTv(
                    viewModel = viewModel.imageButtonTitle,
                    color = Color.White
                )
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VMDButtonTv(
                    viewModel = viewModel.textImageButton
                ) { content, isFocused ->
                    val contentColor = when (isFocused) {
                        true -> MaterialTheme.colorScheme.inverseOnSurface
                        false -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LocalImage(
                            modifier = Modifier.padding(end = 8.dp),
                            imageResource = content.image,
                            contentDescription = content.contentDescription,
                            colorFilter = ColorFilter.tint(contentColor)
                        )
                        Text(
                            text = content.text,
                            style = SampleTextStyle.body.medium()
                        )
                    }
                }
                VMDTextTv(
                    viewModel = viewModel.textImageButtonTitle,
                    color = Color.White
                )
            }
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VMDButtonTv(
                    viewModel = viewModel.textPairButton
                ) { content, _ ->

                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = content.first,
                            style = SampleTextStyle.title2,
                        )
                        Text(
                            text = content.second,
                            style = SampleTextStyle.body,
                        )
                    }
                }
                VMDTextTv(
                    viewModel = viewModel.textPairButtonTitle,
                    color = Color.White
                )
            }
        }
    }
}
