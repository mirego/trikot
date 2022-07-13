package com.mirego.trikot.viewmodels.adapter

import androidx.recyclerview.widget.DiffUtil

class NoDiffCallback<T: Any> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = false

    override fun areContentsTheSame(oldItem: T, newItem: T) = false
}
