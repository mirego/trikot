package com.mirego.trikot.viewmodels

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.viewmodels.utils.BindingUtils

object PickerViewModelBinder {

    @JvmStatic
    @BindingAdapter("view_model")
    fun bind(
        spinner: Spinner,
        pickerViewModel: PickerViewModel<*>?
    ) {
        bind(spinner, pickerViewModel, BindingUtils.getLifecycleOwnerWrapperFromView(spinner))
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        picker: Spinner,
        pickerViewModel: PickerViewModel<*>?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        pickerViewModel?.let { viewModel ->
            picker.bindViewModel(viewModel as ViewModel, lifecycleOwnerWrapper)

            val arrayAdapter = ArrayAdapter<String>(
                picker.context,
                android.R.layout.simple_spinner_item
            ).also { picker.adapter = it }

            viewModel.elements.observe(lifecycleOwnerWrapper.lifecycleOwner) { list ->
                arrayAdapter.clear()
                arrayAdapter.addAll(list.map { it.displayName })
            }

            viewModel.selectedElementIndex.observe(
                lifecycleOwnerWrapper.lifecycleOwner,
                picker::setSelection
            )

            viewModel.enabled.observe(lifecycleOwnerWrapper.lifecycleOwner, picker::setEnabled)

            picker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) = viewModel.setSelectedElementIndex(position)

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }
}
