package com.mirego.trikot.viewmodels

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.mirego.trikot.streams.reactive.asLiveData
import com.mirego.trikot.streams.reactive.just
import com.mirego.trikot.streams.reactive.observe
import com.mirego.trikot.viewmodels.mutable.MutableViewModel
import com.mirego.trikot.viewmodels.properties.ViewModelAction

private val NoViewModel = MutableViewModel()
    .apply { hidden = true.just() } as ViewModel

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

        bindAction(it, lifecycleOwnerWrapper)

        it.backgroundColor.asLiveData()
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
                    isClickable = false
                    postDelayed({ isClickable = true }, NEXT_CLICK_THRESHOLD)
                    action.execute(this)
                }
            }
        }
    }
}

const val NEXT_CLICK_THRESHOLD = 200L
