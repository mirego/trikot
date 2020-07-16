package com.mirego.trikot.viewmodels.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mirego.trikot.viewmodels.ListItemViewModel

class GenericViewModelDiffCallback<MLI : ListItemViewModel> : DiffUtil.ItemCallback<MLI>() {

    override fun areItemsTheSame(oldItem: MLI, newItem: MLI) = oldItem.isTheSame(newItem)

    override fun areContentsTheSame(oldItem: MLI, newItem: MLI) =
        oldItem.haveTheSameContent(newItem)
}
