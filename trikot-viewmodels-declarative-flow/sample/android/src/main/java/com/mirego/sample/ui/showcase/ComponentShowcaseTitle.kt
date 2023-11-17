package com.mirego.sample.ui.showcase

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.trikot.viewmodels.declarative.components.VMDTextViewModel
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun ComponentShowcaseTitle(textViewModel: VMDTextViewModel) {
    VMDText(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        viewModel = textViewModel,
        style = SampleTextStyle.title2
    )
}

@Composable
fun ComponentShowcaseTitle(text: String) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
        text = text,
        style = SampleTextStyle.title2
    )
}
