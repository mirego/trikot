package com.mirego.sample.ui.showcase.components.picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mirego.sample.ui.showcase.ComponentShowcaseTitle
import com.mirego.sample.ui.showcase.ComponentShowcaseTopBar
import com.mirego.sample.viewmodels.showcase.components.picker.PickerShowcaseViewModel
import com.mirego.trikot.viewmodels.declarative.compose.extensions.observeAsState
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDDropDownMenu
import com.mirego.trikot.viewmodels.declarative.compose.viewmodel.VMDDropDownMenuItem

@Composable
fun PickerShowcaseView(pickerShowcaseViewModel: PickerShowcaseViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val viewModel: PickerShowcaseViewModel by pickerShowcaseViewModel.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {
        ComponentShowcaseTopBar(viewModel)

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
    }
}
