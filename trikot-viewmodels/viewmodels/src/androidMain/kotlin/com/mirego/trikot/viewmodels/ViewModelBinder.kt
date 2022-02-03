package com.mirego.trikot.viewmodels

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.viewmodels.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction
import com.mirego.trikot.viewmodels.utils.BindingUtils

private val NoViewModel = MutableViewModel()
    .apply { hidden = true.just() } as ViewModel

@BindingAdapter("view_model")
fun View.bindViewModel(
    viewModel: ViewModel?
) {
    bindViewModel(viewModel, BindingUtils.getLifecycleOwnerWrapperFromView(this))
}

@BindingAdapter("view_model", "lifecycleOwnerWrapper")
fun View.bindViewModel(
    viewModel: ViewModel?,
    lifecycleOwnerWrapper: LifecycleOwnerWrapper
) {
    (viewModel ?: NoViewModel).let {
        it.hidden.observe(lifecycleOwnerWrapper.lifecycleOwner) { isHidden ->
            visibility = if (isHidden) View.GONE else View.VISIBLE
        }

        it.alpha.observe(lifecycleOwnerWrapper.lifecycleOwner) { alpha ->
            setAlpha(alpha)
        }

        it.isAccessibilityElement.observe(lifecycleOwnerWrapper.lifecycleOwner) { isAccessibilityElement ->
            importantForAccessibility = if (isAccessibilityElement) View.IMPORTANT_FOR_ACCESSIBILITY_YES else View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }

        it.accessibilityLabel.observe(lifecycleOwnerWrapper.lifecycleOwner) { accessibilityLabel ->
            contentDescription = accessibilityLabel
        }

        it.accessibilityHint.observe(lifecycleOwnerWrapper.lifecycleOwner) { accessibilityHint ->
            ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, accessibilityHint.hint, null)

            if (accessibilityHint.announceHintChanges && isAccessibilityFocused) {
                announceForAccessibility(accessibilityHint.customHintsChangeAnnouncement ?: accessibilityHint.hint)
            }
        }

        bindAction(it, lifecycleOwnerWrapper)

        it.backgroundColor
            .observe(lifecycleOwnerWrapper.lifecycleOwner) { selector ->
                if (selector.isEmpty) {
                    return@observe
                }

                background ?: run {
                    ViewCompat.setBackground(this, ColorDrawable(Color.WHITE))
                }

                ViewCompat.setBackgroundTintList(this, selector.toColorStateList())
            }
    }
}

fun View.bindAction(viewModel: ViewModel, lifecycleOwnerWrapper: LifecycleOwnerWrapper) {
    viewModel.action.observe(lifecycleOwnerWrapper.lifecycleOwner) { action ->
        when (action) {
            ViewModelAction.None -> {
                setOnClickListener(null)
                isClickable = false
            }
            else -> setOnClickListener { view ->
                with(view) {
                    action.execute(this)
                }
            }
        }
    }
}
