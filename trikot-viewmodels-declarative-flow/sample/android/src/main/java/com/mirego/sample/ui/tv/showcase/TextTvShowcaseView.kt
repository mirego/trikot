package com.mirego.sample.ui.tv.showcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.bold
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.components.text.TextShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDTextTv

@ExperimentalTvMaterial3Api
@Composable
fun TextTvShowcaseView(textShowcaseViewModel: TextShowcaseViewModel) {
    val viewModel: TextShowcaseViewModel by textShowcaseViewModel.observeAsState()

    val textItems = listOf(
        viewModel.largeTitle to SampleTextStyle.largeTitle,
        viewModel.title1 to SampleTextStyle.title1,
        viewModel.title1Bold to SampleTextStyle.title1.bold(),
        viewModel.title2 to SampleTextStyle.title2,
        viewModel.title2Bold to SampleTextStyle.title2.bold(),
        viewModel.title3 to SampleTextStyle.title3,
        viewModel.body to SampleTextStyle.body,
        viewModel.bodyMedium to SampleTextStyle.body.medium(),
        viewModel.button to SampleTextStyle.button,
        viewModel.callout to SampleTextStyle.callout,
        viewModel.subheadline to SampleTextStyle.subheadline,
        viewModel.footnote to SampleTextStyle.footnote,
        viewModel.caption1 to SampleTextStyle.caption1,
        viewModel.caption2 to SampleTextStyle.caption2,
        viewModel.richText to SampleTextStyle.body,
    )

    TvLazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(textItems) { textItem ->
            Card(
                onClick = { }
            ) {
                VMDTextTv(
                    modifier = Modifier.padding(16.dp),
                    viewModel = textItem.first,
                    style = textItem.second
                )
            }
        }
    }
}
