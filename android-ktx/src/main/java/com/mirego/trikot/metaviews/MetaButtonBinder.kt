package com.mirego.trikot.metaviews

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mirego.trikot.drawableBottom
import com.mirego.trikot.drawableEnd
import com.mirego.trikot.drawableStart
import com.mirego.trikot.drawableTop
import com.mirego.trikot.metaviews.mutable.MutableMetaButton
import com.mirego.trikot.metaviews.properties.Alignment
import com.mirego.trikot.metaviews.properties.MetaAction
import com.mirego.trikot.streams.android.ktx.asLiveData
import com.mirego.trikot.streams.android.ktx.observe
import com.mirego.trikot.streams.reactive.*
import com.mirego.trikot.streams.reactive.processors.combine

object MetaButtonBinder {

    val NoMetaButton = MutableMetaButton().apply { hidden = true.just() } as MetaButton
    val NoMetaAction = Publishers.behaviorSubject(MetaAction {})

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        textView: TextView,
        metaButton: MetaButton?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (metaButton ?: NoMetaButton).let { it ->
            bind(textView as View, it, lifecycleOwnerWrapper)

            it.richText?.observe(lifecycleOwnerWrapper.lifecycleOwner) { richText ->
                textView.text = richText.asSpannableString()
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

            it.textColor.asLiveData()
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                    selector.default?.let {
                        textView.setTextColor(it.toIntColor())
                    }
                }
        }
    }

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(view: View, metaButton: MetaButton?, lifecycleOwnerWrapper: LifecycleOwnerWrapper) {
        (metaButton ?: NoMetaButton).let { it ->

            view.bindMetaView(it, lifecycleOwnerWrapper)

            it.enabled
                .distinctUntilChanged()
                .asLiveData()
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { view.isEnabled = it }

            it.selected
                .distinctUntilChanged()
                .asLiveData()
                .observe(lifecycleOwnerWrapper.lifecycleOwner) { view.isSelected = it }
        }
    }

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        imageView: ImageView,
        metaButton: MetaButton?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (metaButton ?: NoMetaButton).let { it ->
            bind(imageView as View, it, lifecycleOwnerWrapper)
            it.imageResource.combine(it.tintColor)
                .map { it.first?.asDrawable(imageView.context, it.second) }
                .observe(lifecycleOwnerWrapper.lifecycleOwner) {
                    imageView.setImageDrawable(it)
                }
        }
    }

    @JvmStatic
    @BindingAdapter("meta_view", "lifecycleOwnerWrapper")
    fun bind(
        button: Button,
        metaButton: MetaButton?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        (metaButton ?: NoMetaButton).let { it ->
            bind(button as View, it, lifecycleOwnerWrapper)

            it.richText?.observe(lifecycleOwnerWrapper.lifecycleOwner) { richText ->
                button.text = richText.asSpannableString()
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

                    if (alignment == null || selector == null) return@observe

                    val drawable = selector.asDrawable(button.context, tintColor)

                    with(button) {
                        when (alignment) {
                            Alignment.LEFT -> drawableStart =
                                drawable
                            Alignment.TOP -> drawableTop =
                                drawable
                            Alignment.RIGHT -> drawableEnd =
                                drawable
                            Alignment.BOTTOM -> drawableBottom =
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
                if (selector.hasAnyValue) {
                    button.backgroundTintList = selector.toColorStateList()
                }
            }

            it.textColor.observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                if (selector.hasAnyValue) {
                    button.setTextColor(selector.toColorStateList())
                }
            }

            it.backgroundImageResource.observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                if (selector.hasAnyValue) {
                    button.background = selector.asDrawable(button.context, null)
                }
            }
        }
    }
}
