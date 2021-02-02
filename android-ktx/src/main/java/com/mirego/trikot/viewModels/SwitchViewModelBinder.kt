package com.mirego.trikot.viewmodels

import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.android.ktx.observe
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.mutable.MutableSwitchViewModel

object SwitchViewModelBinder {
    private val noSwitchViewModel =
        MutableSwitchViewModel().apply { hidden = true.just() } as SwitchViewModel

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        switch: SwitchCompat,
        switchViewModel: SwitchViewModel,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (switchViewModel ?: noSwitchViewModel).let { viewModel ->
            switch.bindViewModel(viewModel as ViewModel, lifecycleOwnerWrapper)
            switch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeState(isChecked)
            }
            viewModel.isOn.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                switch.isChecked = it
            }

        }
    }
}