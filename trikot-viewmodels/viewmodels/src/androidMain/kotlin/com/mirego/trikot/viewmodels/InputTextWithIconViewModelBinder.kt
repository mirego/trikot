package com.mirego.trikot.viewmodels

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.viewmodels.mutable.MutableInputTextWithIconViewModel
import com.mirego.trikot.viewmodels.utils.BindingUtils

object InputTextWithIconViewModelBinder {
    val NoInputTextWithIconViewModel = MutableInputTextWithIconViewModel().apply { hidden = true.just() } as InputTextWithIconViewModel


    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        editText: EditText,
        inputTextViewModel: InputTextWithIconViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        InputTextViewModelBinder.bind(editText, inputTextViewModel, lifecycleOwnerWrapper)
        (inputTextViewModel ?: NoInputTextWithIconViewModel).let {
            ButtonViewModelBinder.bindImage(it, lifecycleOwnerWrapper, editText)
        }
    }

    @JvmStatic
    @BindingAdapter("view_model")
    fun bind(
        editText: EditText,
        inputTextViewModel: InputTextWithIconViewModel?
    ) {
        bind(editText, inputTextViewModel, BindingUtils.getLifecycleOwnerWrapperFromView(editText))
    }
}
