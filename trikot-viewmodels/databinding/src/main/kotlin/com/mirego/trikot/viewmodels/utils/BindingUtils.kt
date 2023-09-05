package com.mirego.trikot.viewmodels.utils

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mirego.trikot.viewmodels.LifecycleOwnerWrapper

object BindingUtils {

    fun getLifecycleOwnerWrapperFromView(view: View): LifecycleOwnerWrapper {
        val binding = DataBindingUtil.findBinding<ViewDataBinding>(view)
            ?: throw IllegalStateException(viewDataBindingNotFound(view))

        val lifecycleOwner = binding.lifecycleOwner
            ?: throw IllegalStateException(lifecycleOwnerNotSet(binding::class.java.simpleName))

        return LifecycleOwnerWrapper(lifecycleOwner)
    }

    private fun viewDataBindingNotFound(view: View): String =
        "${view::class.java.simpleName} with id ${getViewId(view)} is not contained within a <layout> tag"

    private fun lifecycleOwnerNotSet(bindingType: String): String =
        "Lifecycle owner not set in $bindingType"

    private fun getViewId(view: View): String =
        if (view.id == View.NO_ID) {
            "NO_ID"
        } else {
            view.resources.getResourceName(view.id)
        }
}
