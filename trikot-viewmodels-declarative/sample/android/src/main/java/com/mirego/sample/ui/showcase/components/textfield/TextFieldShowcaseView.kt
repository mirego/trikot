package com.mirego.sample.ui.showcase.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mirego.sample.resource.SampleImageProvider
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseViewModel
import com.mirego.sample.viewmodels.showcase.components.textfield.TextFieldShowcaseViewModelPreview
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDTextField
import com.mirego.trikot.viewmodels.declarative.configuration.TrikotViewModelDeclarative

@Composable
fun TextFieldShowcaseView(textFieldShowcaseViewModel: TextFieldShowcaseViewModel) {
    val viewModel: TextFieldShowcaseViewModel by textFieldShowcaseViewModel.observeAsState()

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(Modifier.padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    VMDTextField(viewModel = viewModel.textField)

                    VMDButton(
                        modifier = Modifier.padding(start = 16.dp),
                        viewModel = viewModel.clearButton
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

                VMDText(viewModel = viewModel.characterCountText, style = SampleTextStyle.caption1)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "Material 3",
                    style = SampleTextStyle.largeTitle
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDTextField(viewModel = viewModel.textField)

                    VMDButton(
                        modifier = Modifier.padding(start = 16.dp),
                        viewModel = viewModel.clearButton
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

                com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDText(viewModel = viewModel.characterCountText, style = SampleTextStyle.caption1)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TextFieldShowcaseViewPreview() {
    TrikotViewModelDeclarative.initialize(SampleImageProvider())
    TextFieldShowcaseView(textFieldShowcaseViewModel = TextFieldShowcaseViewModelPreview())
}
