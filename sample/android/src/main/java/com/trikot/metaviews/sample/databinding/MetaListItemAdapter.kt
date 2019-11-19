package com.trikot.metaviews.sample.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trikot.metaviews.sample.R
import com.trikot.metaviews.sample.metaviews.*

class MetaListItemAdapter :
    DataBindingAdapter<MetaListItem>(diffCallback = DefaultDiffUtilCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MetaNavigableListItem -> R.layout.item_navigable
            is MetaLabelListItem -> R.layout.item_label
            is MetaHeaderListItem -> R.layout.item_header
            is MetaViewListItem -> R.layout.item_view
            is MetaButtonListItem -> R.layout.item_button
            is MetaImageListItem -> R.layout.item_image
            is MetaInputTextListItem -> R.layout.item_input_text
            else -> TODO()
        }
    }
}

@BindingAdapter("items")
fun bind(recyclerView: RecyclerView, data: List<MetaListItem>?) {
    data?.let {
        recyclerView.adapter?.let { (it as MetaListItemAdapter).submitList(data) }
    }
}
