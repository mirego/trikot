package com.mirego.trikot.viewmodels

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mirego.trikot.streams.reactive.observe

object ListBinding {
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    @BindingAdapter("view_model", "lifecycleOwnerWrapper")
    fun bindList(
        view: RecyclerView,
        listViewModel: ListViewModel<*>?,
        lifecycleOwnerWrapper: LifecycleOwnerWrapper
    ) {
        view.bindViewModel(listViewModel, lifecycleOwnerWrapper)
        listViewModel?.elements
            ?.observe(lifecycleOwnerWrapper.lifecycleOwner) { items ->
                view.adapter?.let {
                    (it as? ListAdapter<ListItemViewModel, *>)?.submitList(items)
                }
            }
    }
}
