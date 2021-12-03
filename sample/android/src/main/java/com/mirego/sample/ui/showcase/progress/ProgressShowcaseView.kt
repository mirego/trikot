package com.mirego.sample.ui.showcase.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.viewmodels.showcase.progress.ProgressShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDCircularProgressIndicator
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDLinearProgressIndicator

@Composable
fun ProgressShowcaseView(progressShowcaseViewModel: ProgressShowcaseViewModel) {
    val viewModel: ProgressShowcaseViewModel by progressShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {

        ComponentShowcaseTopBar(viewModel)

        ComponentShowcaseTitle(viewModel.linearDeterminateProgressTitle)

        VMDLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.determinateProgress
        )

        ComponentShowcaseTitle(viewModel.linearIndeterminateProgressTitle)

        VMDLinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.indeterminateProgress
        )

        ComponentShowcaseTitle(viewModel.circularDeterminateProgressTitle)

        VMDCircularProgressIndicator(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.determinateProgress
        )

        ComponentShowcaseTitle(viewModel.circularIndeterminateProgressTitle)

        VMDCircularProgressIndicator(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            progressViewModel = viewModel.indeterminateProgress
        )
    }
}
