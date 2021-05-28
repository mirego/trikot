package com.mirego.trikot.viewmodels

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.map
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.streams.reactive.processors.combine
import com.mirego.trikot.viewmodels.mutable.MutableButtonViewModel
import com.mirego.trikot.viewmodels.properties.Alignment
import com.mirego.trikot.viewmodels.properties.ViewModelAction

object ButtonViewModelBinder {

    val NoButtonViewModel = MutableButtonViewModel().apply { hidden = true.just() } as ButtonViewModel
    val NoViewModelAction = Publishers.behaviorSubject(ViewModelAction {})

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        textView: TextView,
        buttonViewModel: ButtonViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (buttonViewModel ?: NoButtonViewModel).let { it ->
            bind(textView as View, it, lifecycleOwnerWrapper)

            it.richText?.observe(lifecycleOwnerWrapper.lifecycleOwner) { richText ->
                textView.text = richText.asSpannableString(textView.context)
            }

            it.takeUnless { it.richText != null }?.text
                ?.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                    textView.text = it
                }

            it.imageAlignment.combine(it.imageResource).combine(it.tintColor)
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { observeResult ->
                    val alignment = observeResult.first?.first
                    val selector = observeResult.first?.second
                    val tintColor = observeResult.second

                    if (alignment == null || selector == null) return@observe

                    val drawable = selector.asDrawable(textView.context, tintColor)
                    with(textView) {
                        when (alignment) {
                            Alignment.LEFT -> drawableStart = drawable
                            Alignment.TOP -> drawableTop = drawable
                            Alignment.RIGHT -> drawableEnd = drawable
                            Alignment.BOTTOM -> drawableBottom = drawable
                            Alignment.CENTER -> drawableStart = drawable
                            else -> {
                                drawableStart = null
                                drawableTop = null
                                drawableEnd = null
                                drawableBottom = null
                            }
                        }
                    }
                }

            it.textColor
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                    selector.default?.let {
                        textView.setTextColor(it.toIntColor())
                    }
                }
        }
    }

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(view: View, buttonViewModel: ButtonViewModel?, lifecycleOwnerWrapper: LifecycleOwnerWrapper) {
        (buttonViewModel ?: NoButtonViewModel).let { it ->

            view.bindViewModel(it, lifecycleOwnerWrapper)

            it.enabled
                .distinctUntilChanged()
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { view.isEnabled = it }

            it.selected
                .distinctUntilChanged()
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { view.isSelected = it }
        }
    }

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        imageView: ImageView,
        buttonViewModel: ButtonViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (buttonViewModel ?: NoButtonViewModel).let { it ->
            bind(imageView as View, it, lifecycleOwnerWrapper)
            it.imageResource.combine(it.tintColor)
                .map { it.first?.asDrawable(imageView.context, it.second) }
                .observe(lifecycleOwnerWrapper.lifecycleOwner) {
                    imageView.setImageDrawable(it)
                }
        }
    }

    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bind(
        button: Button,
        buttonViewModel: ButtonViewModel?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (buttonViewModel ?: NoButtonViewModel).let { it ->
            bind(button as View, it, lifecycleOwnerWrapper)

            it.richText?.observe(lifecycleOwnerWrapper.lifecycleOwner) { richText ->
                button.text = richText.asSpannableString(button.context)
            }

            it.takeUnless { it.richText != null }?.text
                ?.observe(lifecycleOwnerWrapper.lifecycleOwner) {
                    button.text = it
                }

            it.imageAlignment.combine(it.imageResource).combine(it.tintColor)
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { observeResult ->
                    val alignment = observeResult.first?.first
                    val selector = observeResult.first?.second
                    val tintColor = observeResult.second

                    if (alignment == null || (selector == null || selector.isEmpty)) return@observe

                    val drawable = selector.asDrawable(button.context, tintColor)

                    with(button) {
                        when (alignment) {
                            Alignment.LEFT ->
                                drawableStart =
                                    drawable
                            Alignment.TOP ->
                                drawableTop =
                                    drawable
                            Alignment.RIGHT ->
                                drawableEnd =
                                    drawable
                            Alignment.BOTTOM ->
                                drawableBottom =
                                    drawable
                            Alignment.CENTER -> drawableStart = drawable
                            else -> {
                                drawableStart = null
                                drawableTop = null
                                drawableEnd = null
                                drawableBottom = null
                            }
                        }
                    }
                }

            it.backgroundColor.observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                if (!selector.isEmpty) {
                    ViewCompat.setBackgroundTintList(button, selector.toColorStateList())
                }
            }

            it.textColor.observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                if (!selector.isEmpty) {
                    button.setTextColor(selector.toColorStateList())
                }
            }

            it.backgroundImageResource.observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                if (!selector.isEmpty) {
                    ViewCompat.setBackground(button, selector.asDrawable(button.context, null))
                }
            }
        }
    }
}
