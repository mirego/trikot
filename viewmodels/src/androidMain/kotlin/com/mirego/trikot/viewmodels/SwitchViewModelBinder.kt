package com.mirego.trikot.viewmodels

import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.asLiveData
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.viewmodels.mutable.MutableToggleSwitchViewModel

object SwitchViewModelBinder {
    private val noSwitchViewModel =
        MutableToggleSwitchViewModel().apply { hidden = true.just() } as ToggleSwitchViewModel

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        switch: SwitchCompat,
        switchViewModel: ToggleSwitchViewModel,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        switchViewModel.let { viewModel ->
            switch.bindViewModel(viewModel as ViewModel, lifecycleOwnerWrapper)
            switch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setIsOn(isChecked)
            }
            viewModel.isOn.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                switch.isChecked = it
            }

            viewModel.enabled
                .asLiveData()
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { switch.isEnabled = it }
        }
    }
}
