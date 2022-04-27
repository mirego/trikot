package com.mirego.sample.ui.showcase.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.viewmodels.showcase.components.list.ListShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDLazyColumnIndexed
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDLazyRow
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDText

@Composable
fun ListShowcaseView(listShowcaseViewModel: ListShowcaseViewModel) {
    val viewModel: ListShowcaseViewModel by listShowcaseViewModel.observeAsState()

    Box(
        Modifier.fillMaxSize()
    ) {

        ComponentShowcaseTopBar(viewModel)

        VMDLazyRow(
            modifier = Modifier.padding(top = 80.dp),
            viewModel = viewModel.listViewModel,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) { element ->
            VMDText(viewModel = element.content)
        }

        VMDLazyColumnIndexed(
            modifier = Modifier
                .padding(top = 150.dp)
                .fillMaxWidth(),
            viewModel = viewModel.listViewModel,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) { index, element ->
            Column {
                if (index != 0) {
                    Divider(modifier = Modifier.padding(bottom = 16.dp))
                }
                VMDText(viewModel = element.content)
            }
        }
    }
}
