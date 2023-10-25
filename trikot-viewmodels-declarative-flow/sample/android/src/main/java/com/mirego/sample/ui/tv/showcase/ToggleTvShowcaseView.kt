package com.mirego.sample.ui.tv.showcase

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.mirego.sample.viewmodels.showcase.components.toggle.ToggleShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDSwitchTv

@ExperimentalTvMaterial3Api
@Composable
fun ToggleTvShowcaseView(toggleShowcaseViewModel: ToggleShowcaseViewModel) {
    val viewModel: ToggleShowcaseViewModel by toggleShowcaseViewModel.observeAsState()

    TvLazyColumn {
        item {
            VMDSwitchTv(
                modifier = Modifier.padding(start = 10.dp, top = 16.dp, end = 16.dp),
                viewModel = viewModel.emptyToggle
            )
        }
    }
}
