package com.mirego.trikot.viewmodels

import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.streams.reactive.promise.Promise
import com.mirego.trikot.viewmodels.mutable.MutableToggleSwitchViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.utils.BindingUtils

object ToggleSwitchViewModelBinder {
    private val noSwitchViewModel =
        MutableToggleSwitchViewModel().apply { hidden = true.just() } as ToggleSwitchViewModel

    @JvmStatic
    @BindingAdapter("view_model")
    fun bind(
        toggleSwitch: SwitchCompat,
        toggleSwitchViewModel: ToggleSwitchViewModel
    ) {
        bind(toggleSwitch, toggleSwitchViewModel, BindingUtils.getLifecycleOwnerWrapperFromView(toggleSwitch))
    }

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        toggleSwitch: SwitchCompat,
        toggleSwitchViewModel: ToggleSwitchViewModel,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        toggleSwitch.bindViewModel(toggleSwitchViewModel, lifecycleOwnerWrapper)

        toggleSwitchViewModel.toggleSwitchAction.observe(lifecycleOwnerWrapper.lifecycleOwner) { action ->
            when (action) {
                ViewModelAction.None -> {
                    toggleSwitch.setOnClickListener(null)
                    toggleSwitch.isClickable = false
                }
                else -> toggleSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    with(buttonView) {
                        Promise.from(toggleSwitchViewModel.checked).onSuccess {
                            if (isChecked != it) {
                                toggleSwitch.isChecked = it
                                action.execute(this)
                            }
                        }
                    }
                }
            }
        }

        toggleSwitchViewModel.checked.distinctUntilChanged()
            .observe(lifecycleOwnerWrapper.lifecycleOwner) { checked ->
                toggleSwitch.isChecked = checked
            }

        toggleSwitchViewModel.enabled.observe(lifecycleOwnerWrapper.lifecycleOwner) { enabled ->
            toggleSwitch.isEnabled = enabled
        }
    }
}
