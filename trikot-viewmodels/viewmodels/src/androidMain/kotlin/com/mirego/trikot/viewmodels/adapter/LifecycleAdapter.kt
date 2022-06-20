package com.mirego.trikot.viewmodels.adapter

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class LifecycleAdapter<T, VH : LifecycleAdapter.LifecycleViewHolder>(
    private var lifecycleOwner: LifecycleOwner,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    ListAdapter<T, VH>(diffCallback) {

    private val viewHolders = mutableSetOf<LifecycleViewHolder>()

    init {
        lifecycleOwner.lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    viewHolders.forEach {
                        it.destroy()
                    }
                    viewHolders.clear()
                    lifecycleOwner.lifecycle.removeObserver(this)
                }
            }
        )
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.attach()
        viewHolders.add(holder)
    }

    @CallSuper
    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.attach()
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.detach()
    }

    @CallSuper
    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.destroy()
        viewHolders.remove(holder)
    }

    @Suppress("LeakingThis")
    abstract class LifecycleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), LifecycleOwner {
        private var lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        override fun getLifecycle() = lifecycleRegistry

        open fun onAttach() {}

        open fun onDetach() {}

        fun attach() {
            if (lifecycleRegistry.currentState == Lifecycle.State.INITIALIZED) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            }
            if (lifecycleRegistry.currentState == Lifecycle.State.CREATED) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
                onAttach()
            }
        }

        fun detach() {
            if (lifecycleRegistry.currentState == Lifecycle.State.RESUMED) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                onDetach()
            }
        }

        fun destroy() {
            detach()
            if (lifecycleRegistry.currentState == Lifecycle.State.CREATED) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                lifecycleRegistry = LifecycleRegistry(this)
            }
        }
    }
}
