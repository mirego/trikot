package com.trikot.viewmodels.sample.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBindingAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T> = DefaultDiffUtilCallback()
) : ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: DataBindingViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onViewDetachedFromWindow(holder: DataBindingViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        holder.detach()
    }

    override fun onViewAttachedToWindow(holder: DataBindingViewHolder<T>) {
        super.onViewAttachedToWindow(holder)
        holder.attach()
    }
}

class DefaultDiffUtilCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = false
    override fun areContentsTheSame(oldItem: T, newItem: T) = false
}

