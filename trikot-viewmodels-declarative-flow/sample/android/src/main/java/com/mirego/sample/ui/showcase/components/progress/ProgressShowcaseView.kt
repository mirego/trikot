package com.mirego.sample.ui.showcase.components.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.progress.ProgressShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDCircularProgressIndicator
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDLinearProgressIndicator

@Composable
fun ProgressShowcaseView(progressShowcaseViewModel: ProgressShowcaseViewModel) {
    val viewModel: ProgressShowcaseViewModel by progressShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ComponentShowcaseTopBar(viewModel)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ComponentShowcaseTitle(viewModel.linearDeterminateProgressTitle)

            VMDLinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                viewModel = viewModel.determinateProgress
            )

            ComponentShowcaseTitle(viewModel.linearIndeterminateProgressTitle)

            VMDLinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                viewModel = viewModel.indeterminateProgress
            )

            ComponentShowcaseTitle(viewModel.circularDeterminateProgressTitle)

            VMDCircularProgressIndicator(
                modifier = Modifier,
                viewModel = viewModel.determinateProgress
            )

            ComponentShowcaseTitle(viewModel.circularIndeterminateProgressTitle)

            VMDCircularProgressIndicator(
                viewModel = viewModel.indeterminateProgress
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Material 3",
                style = SampleTextStyle.largeTitle
            )

            com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDLinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                viewModel = viewModel.determinateProgress
            )

            ComponentShowcaseTitle(viewModel.linearIndeterminateProgressTitle)

            com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDLinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(),
                viewModel = viewModel.indeterminateProgress
            )

            ComponentShowcaseTitle(viewModel.circularDeterminateProgressTitle)

            com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDCircularProgressIndicator(
                modifier = Modifier,
                viewModel = viewModel.determinateProgress
            )

            ComponentShowcaseTitle(viewModel.circularIndeterminateProgressTitle)

            com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDCircularProgressIndicator(
                viewModel = viewModel.indeterminateProgress
            )
        }


    }
}

@Preview(showSystemUi = true)
@Composable
fun ProgressShowcaseViewPreview() {
    ProgressShowcaseView(progressShowcaseViewModel = ProgressShowcaseViewModelPreview())
}
