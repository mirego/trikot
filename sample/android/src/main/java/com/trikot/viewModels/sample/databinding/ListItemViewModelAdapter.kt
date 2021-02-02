package com.trikot.viewmodels.sample.databinding

import androidx.lifecycle.LifecycleOwner
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.adapter.GenericViewModelDiffCallback
import com.mirego.trikot.viewmodels.adapter.ViewModelAdapter
import com.trikot.viewmodels.sample.BR
import com.trikot.viewmodels.sample.R
import com.trikot.viewmodels.sample.viewmodels.*

class ListItemViewModelAdapter(lifecycleOwner: LifecycleOwner) :
    ViewModelAdapter<ListItemViewModel>(
        viewModelVariableId = BR.viewModel,
        lifecycleVariableId = BR.lifecycleOwnerWrapper,
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
                is InputTextListItemViewModel -> R.layout.item_input_text
                is SliderListItemViewModel -> R.layout.item_slider
                is SwitchListItemViewModel -> R.layout.item_switch
                else -> TODO()
            }
        }
    )
