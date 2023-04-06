package com.mirego.sample.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.ui.theming.medium
import com.mirego.sample.viewmodels.dialog.DialogViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDButton
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun DialogView(dialogViewModel: DialogViewModel) {
    val viewModel by dialogViewModel.observeAsState()

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        VMDText(
            viewModel = viewModel.message,
            style = SampleTextStyle.title2,
            textAlign = TextAlign.Center
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            VMDButton(
                viewModel = viewModel.leftButton
            ) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    text = it.text,
                    style = SampleTextStyle.body.medium(),
                    color = MaterialTheme.colors.onPrimary
                )
            }
            VMDButton(
                viewModel = viewModel.rightButton
            ) {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(6.dp))
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    text = it.text,
                    style = SampleTextStyle.body.medium(),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }

}
