package com.mirego.trikot.viewmodels

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.viewmodels.mutable.MutableLabelViewModel
import com.mirego.trikot.viewmodels.utils.BindingUtils

object LabelViewModelBinder {

    private val NoLabelViewModel = MutableLabelViewModel().apply { hidden = true.just() } as LabelViewModel

    @JvmStatic
    @BindingAdapter("view_model")
    fun bind(
        textView: TextView,
        labelViewModel: LabelViewModel?
    ) {
        bind(textView, labelViewModel, BindingUtils.getLifecycleOwnerWrapperFromView(textView))
    }

    @JvmStatic
    @BindingAdapter("view_model", "hiddenVisibility", "lifecycleOwnerWrapper")
    fun bind(
        textView: TextView,
        labelViewModel: LabelViewModel?,
        hiddenVisibility: HiddenVisibility,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        val label = labelViewModel ?: NoLabelViewModel

        label.richText?.observe(lifecycleOwnerWrapper.lifecycleOwner) { richText ->
            textView.text = richText.asSpannableString(textView.context)
        }

        label.takeUnless { it.richText != null }?.text
            ?.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                textView.text = it
            }

        label.textColor
            .observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                selector.default?.let {
                    textView.setTextColor(it.toIntColor())
                }
            }

        bindExtraViewProperties(textView, label, hiddenVisibility, lifecycleOwnerWrapper)
    }

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        textView: TextView,
        labelViewModel: LabelViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        bind(textView, labelViewModel, HiddenVisibility.GONE, lifecycleOwnerWrapper)
    }

    @JvmStatic
    fun bindWithoutTextPublishers(
        textView: TextView,
        labelViewModel: LabelViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        bindExtraViewProperties(
            textView,
            labelViewModel ?: NoLabelViewModel,
            HiddenVisibility.GONE,
            lifecycleOwnerWrapper
        )
    }

    @JvmStatic
    private fun bindExtraViewProperties(
        textView: TextView,
        labelViewModel: LabelViewModel,
        hiddenVisibility: HiddenVisibility,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        textView.bindViewModel(labelViewModel, hiddenVisibility, lifecycleOwnerWrapper)
    }
}

enum class HiddenVisibility(val value: Int) {
    GONE(View.GONE),
    INVISIBLE(View.INVISIBLE)
}
