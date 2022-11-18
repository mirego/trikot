package com.mirego.sample.ui.showcase.components.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.components.snackbar.SnackbarShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDSnackbarFlow

@Composable
fun SnackbarShowcaseView(snackbarShowcaseViewModel: SnackbarShowcaseViewModel) {
    val viewModel: SnackbarShowcaseViewModel by snackbarShowcaseViewModel.observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    VMDSnackbarFlow(
        snackbarFlow = viewModel.snackbar,
        snackbarHostState = snackbarHostState
    )

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            ComponentShowcaseTopBar(viewModel)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
        ) {
            viewModel.buttons.forEach { button ->
                VMDButton(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    viewModel = button
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
            }
        }
    }
}
