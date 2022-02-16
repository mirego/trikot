package com.trikot.viewmodels.sample.databinding

import androidx.lifecycle.LifecycleOwner
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.adapter.GenericViewModelDiffCallback
import com.mirego.trikot.viewmodels.adapter.ViewModelAdapter
import com.trikot.viewmodels.sample.BR
import com.trikot.viewmodels.sample.R
import com.trikot.viewmodels.sample.viewmodels.ButtonListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.HeaderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.ImageListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.InputTextListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.InputTextWithIconListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.LabelListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.NavigableListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.PickerListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.SliderListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.ToggleSwitchListItemViewModel
import com.trikot.viewmodels.sample.viewmodels.ViewListItemViewModel

class ListItemViewModelAdapter(lifecycleOwner: LifecycleOwner) :
    ViewModelAdapter<ListItemViewModel>(
        viewModelVariableId = BR.viewModel,
        lifecycleOwner = lifecycleOwner,
        diffCallback = GenericViewModelDiffCallback(),
        layoutMapper = { viewModel ->
            when (viewModel) {
                is NavigableListItemViewModel -> R.layout.item_navigable
                is LabelListItemViewModel -> R.layout.item_label
                is HeaderListItemViewModel -> R.layout.item_header
                is ViewListItemViewModel -> R.layout.item_view
                is ButtonListItemViewModel -> R.layout.item_button
                is ImageListItemViewModel -> R.layout.item_image
                is InputTextWithIconListItemViewModel -> R.layout.item_input_text_with_icon
                is InputTextListItemViewModel -> R.layout.item_input_text
                is SliderListItemViewModel -> R.layout.item_slider
                is ToggleSwitchListItemViewModel -> R.layout.item_switch
                is PickerListItemViewModel -> R.layout.item_picker
                else -> TODO()
            }
        }
    )
