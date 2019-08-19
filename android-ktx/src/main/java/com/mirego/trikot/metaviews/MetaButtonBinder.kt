package com.mirego.trikot.metaviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.StateSet
import android.view.View
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
import com.mirego.trikot.metaviews.properties.MetaSelector
import com.mirego.trikot.metaviews.resource.ImageResource
import com.mirego.trikot.metaviews.resources.MetaImageResourceManager
import com.mirego.trikot.streams.android.ktx.asLiveData
import com.mirego.trikot.streams.android.ktx.observe
import com.mirego.trikot.streams.reactive.CombineLatest
import com.mirego.trikot.streams.reactive.Publishers
import com.mirego.trikot.streams.reactive.distinctUntilChanged
import com.mirego.trikot.streams.reactive.map

object MetaButtonBinder {

    val NoMetaButton = MutableMetaButton().apply { hidden.value = true } as MetaButton
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
            it.text
                    .asLiveData()
                    .observe(lifecycleOwnerWrapper.lifecycleOwner) { textView.text = it }

            CombineLatest.combine2(it.imageAlignment, it.imageResource)
                    .observe(lifecycleOwnerWrapper.lifecycleOwner) { (alignment, selector) ->
                        if (alignment == null || selector == null) return@observe

                        val drawable = selector.asDrawable(textView.context)
                        with(textView) {
                            when (alignment) {
                                Alignment.LEFT -> drawableStart = drawable
                                Alignment.TOP -> drawableTop = drawable
                                Alignment.RIGHT -> drawableEnd = drawable
                                Alignment.BOTTOM -> drawableBottom = drawable
                                else -> {
                                    drawableStart = null
                                    drawableTop = null
                                    drawableEnd = null
                                    drawableBottom = null
                                }
                            }
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
            it.imageResource
                    .map { it.asDrawable(imageView.context) }
                    .observe(lifecycleOwnerWrapper.lifecycleOwner) {
                        imageView.setImageDrawable(it)
                    }
        }
    }
}

fun MetaSelector<ImageResource>.asDrawable(context: Context): Drawable {
    val stateListDrawable = StateListDrawable()

    this.disabled?.let { imageResource ->
        imageResource.asDrawable(context).let { drawable ->
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), drawable)
        }
    }

    this.highlighted?.let { imageResource ->
        imageResource.asDrawable(context).let { drawable ->
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), drawable)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_focused), drawable)
        }
    }

    this.selected?.let { imageResource ->
        imageResource.asDrawable(context).let { drawable ->
            stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), drawable)
        }
    }

    this.default?.let { imageResource ->
        imageResource.asDrawable(context).let { drawable ->
            stateListDrawable.addState(StateSet.WILD_CARD, drawable)
        }
    }

    return stateListDrawable
}

fun ImageResource.resourceId(context: Context): Int? {
    return MetaImageResourceManager.provider.resourceIdFromResource(this, context)
}

fun ImageResource.asDrawable(context: Context): Drawable? {
    return resourceId(context)?.let { resourceId -> context.getDrawable(resourceId) }
}
