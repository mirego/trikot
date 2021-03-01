package com.mirego.trikot.viewmodels

import android.os.Build
import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.viewmodels.mutable.MutableSliderViewModel

object SliderViewModelBinder {
    val noSliderViewModel =
        MutableSliderViewModel().apply { hidden = true.just() } as SliderViewModel

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        seekBar: SeekBar,
        sliderViewModel: SliderViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (sliderViewModel ?: noSliderViewModel).let { viewModel ->
            seekBar.max = viewModel.maxValue
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                seekBar.min = viewModel.minValue
            }

            seekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        viewModel.setSelectedValue(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                }
            )

            viewModel.selectedValue.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                seekBar.progress = it
            }

            seekBar.bindViewModel(viewModel as ViewModel, lifecycleOwnerWrapper)
        }
    }
}
