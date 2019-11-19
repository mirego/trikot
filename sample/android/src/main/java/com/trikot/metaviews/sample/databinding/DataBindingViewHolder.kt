package com.trikot.metaviews.sample.databinding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.mirego.trikot.metaviews.LifecycleOwnerWrapper
import com.trikot.metaviews.sample.BR

class DataBindingViewHolder<T>(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root),
    LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        binding.lifecycleOwner = this
        binding.setVariable(BR.lifecycleOwnerWrapper, LifecycleOwnerWrapper(this))
        lifecycleRegistry.markState(Lifecycle.State.INITIALIZED)
    }

    override fun getLifecycle() = lifecycleRegistry

    fun bind(item: T) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
        attach()
    }

    fun unbind() {
        detach()
    }

    fun attach() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        binding.invalidateAll()
    }

    fun detach() {
        binding.unbind()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}
