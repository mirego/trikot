package com.mirego.sample.ui.tv.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.LocalImage
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDCheckboxTv
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDSwitchTv
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDTextTv

@ExperimentalTvMaterial3Api
@Composable
fun ToggleTvShowcaseView(toggleShowcaseViewModel: ToggleShowcaseViewModel) {
    val viewModel: ToggleShowcaseViewModel by toggleShowcaseViewModel.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TvLazyRow(
            modifier = Modifier
                .align(Alignment.Center)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDCheckboxTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.emptyToggle
                    )
                    VMDTextTv(
                        viewModel = viewModel.checkboxTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDCheckboxTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.textToggle,
                        label = { content ->
                            Text(
                                modifier = Modifier.padding(end = 6.dp),
                                text = content.text
                            )
                        }
                    )
                    VMDTextTv(
                        viewModel = viewModel.textCheckboxTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDCheckboxTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.imageToggle,
                        label = { content ->
                            LocalImage(
                                modifier = Modifier.padding(end = 6.dp),
                                imageResource = content.image,
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                    )
                    VMDTextTv(
                        viewModel = viewModel.imageCheckboxTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDCheckboxTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.textImageToggle,
                        label = { content ->
                            Row(
                                modifier = Modifier.padding(end = 6.dp),
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
                    VMDTextTv(
                        viewModel = viewModel.textImageCheckboxTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDCheckboxTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.textPairToggle,
                        label = { content ->
                            Column(
                                modifier = Modifier.padding(end = 6.dp),
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
                    VMDTextTv(
                        viewModel = viewModel.textPairCheckboxTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDSwitchTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.emptyToggle
                    )
                    VMDTextTv(
                        viewModel = viewModel.switchTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDSwitchTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.textToggle,
                        label = { content ->
                            Text(
                                content.text,
                                style = SampleTextStyle.body
                            )
                        }
                    )
                    VMDTextTv(
                        viewModel = viewModel.textSwitchTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDSwitchTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.imageToggle,
                        label = { content ->
                            LocalImage(
                                modifier = Modifier.padding(end = 6.dp),
                                imageResource = content.image,
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                    )
                    VMDTextTv(
                        viewModel = viewModel.imageSwitchTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDSwitchTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.textImageToggle,
                        label = { content ->
                            Row(
                                modifier = Modifier.padding(end = 6.dp),
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
                    VMDTextTv(
                        viewModel = viewModel.textImageSwitchTitle
                    )
                }
            }
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    VMDSwitchTv(
                        modifier = Modifier
                            .padding(16.dp),
                        viewModel = viewModel.textPairToggle,
                        label = { content ->
                            Column(
                                modifier = Modifier.padding(end = 6.dp),
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
                    VMDTextTv(
                        viewModel = viewModel.textPairSwitchTitle
                    )
                }
            }
        }
    }
}
