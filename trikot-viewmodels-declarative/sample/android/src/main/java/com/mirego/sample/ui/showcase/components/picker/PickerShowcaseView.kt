package com.mirego.sample.ui.showcase.components.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.ui.theming.SampleTextStyle
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDDropDownMenu
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDDropDownMenuItem
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.material3.VMDDropDownMenuItem

@Composable
fun PickerShowcaseView(pickerShowcaseViewModel: PickerShowcaseViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    val viewModel: PickerShowcaseViewModel by pickerShowcaseViewModel.observeAsState()

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
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = true
                    }
            ) {
                ComponentShowcaseTitle(viewModel.textPickerTitle)
            }

            VMDDropDownMenu(
                viewModel = viewModel.textPicker,
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) { item, index ->
                VMDDropDownMenuItem(
                    pickerViewModel = viewModel.textPicker,
                    viewModel = item,
                    index = index,
                    onClick = { expanded = false }
                ) { pickerItem, _ ->
                    Text(text = pickerItem.content.text)
                }
            }

            androidx.compose.material3.Text(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                text = "Material 3",
                style = SampleTextStyle.largeTitle
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded2 = true
                    }
            ) {
                ComponentShowcaseTitle(viewModel.textPickerTitle2)
            }

            VMDDropDownMenu(
                viewModel = viewModel.textPicker2,
                expanded = expanded2,
                onDismissRequest = {
                    expanded2 = false
                }
            ) { item, index ->
                VMDDropDownMenuItem(
                    pickerViewModel = viewModel.textPicker2,
                    viewModel = item,
                    index = index,
                    onClick = { expanded2 = false },
                    text = {
                        Text(text = item.content.text)
                    }
                )
            }
        }
    }
}
