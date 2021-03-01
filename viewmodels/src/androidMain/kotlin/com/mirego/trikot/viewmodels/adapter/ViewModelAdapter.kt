package com.mirego.trikot.viewmodels.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mirego.trikot.viewmodels.LifecycleOwnerWrapper
import com.mirego.trikot.viewmodels.ListItemViewModel
import com.mirego.trikot.viewmodels.bindViewModel

typealias LayoutMapper<MLI> = (viewModel: MLI) -> Int

/**
 * Generic adapter that handles all cases where only binding is used at the layout level.
 * This class extends ListAdapter, which forces the usage of DiffUtil.ItemCallback.
 *
 * @property layoutMapper A lambda used by the adapter to determine view type
 * @property viewModelVariableId The generated ID for the variable of the ViewModel, as declared in the layout file.
 * @property lifecycleVariableId The generated ID for the variable of the LifecycleOwnerWrapper, as declared in the layout file.
 * @property lifecycleOwner The parent lifecycle owner
 *
 * For instance, using the following variable in the layout XML file:
 *
 * <pre>
 * <data>
 *   <variable
 *     name="viewModel"
 *     type="com.example.ExampleViewModel" />
 * </data>
 * </pre>
 *
 * The Int to use would be:
 *
 * <pre>
 *    com.example.BR.viewModel
 * </pre>
 *
 * @constructor diffCallback The ItemCallback to compute diff changes in the adapter.
 * @constructor layoutId The layout to inflate for Adapter item
 *
 * With a layoutMapper, it is possible to have different layouts depending on the ViewModel.
 *
 * Example on how to use it (here it relies on the class of the ViewModel, but anything else can be used):
 *
 * recyclerView.adapter = ViewModelAdapter<ViewModel>(BR.viewModel, BR.lifecycleOwnerWrapper, this, CustomCallbackDiff()) { viewModel ->
 *     when (viewModel) {
 *         is HeaderViewModel -> R.layout.view_header
 *         else -> R.layout.view_item
 *     }
 *  }
 *
 */
open class ViewModelAdapter<MLI : ListItemViewModel>(
    @IdRes private var viewModelVariableId: Int,
    @IdRes private var lifecycleVariableId: Int,
    lifecycleOwner: LifecycleOwner,
    diffCallback: DiffUtil.ItemCallback<MLI> = NoDiffCallback(),
    private val layoutMapper: LayoutMapper<MLI>
) :
    LifecycleAdapter<MLI, ViewModelAdapter<MLI>.ViewHolder<ViewDataBinding>>(lifecycleOwner, diffCallback) {

    constructor(
        @LayoutRes layoutId: Int,
        @IdRes viewModelVariableId: Int,
        @IdRes lifecycleVariableId: Int,
        lifecycleOwner: LifecycleOwner,
        diffCallback: DiffUtil.ItemCallback<MLI> = NoDiffCallback()
    ) : this(
        viewModelVariableId,
        lifecycleVariableId,
        lifecycleOwner,
        diffCallback,
        { layoutId }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position: Int) {
        val item = getItem(position)
        holder.setVariable(item)
        super.onBindViewHolder(holder, position)
        holder.bind(item)
    }

    override fun onViewRecycled(holder: ViewHolder<ViewDataBinding>) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemViewType(position: Int) =
        if (position == RecyclerView.NO_POSITION) RecyclerView.INVALID_TYPE else layoutMapper(
            getItem(position)
        )

    protected fun getItem(holder: RecyclerView.ViewHolder): MLI? {
        val position = holder.adapterPosition
        return if (position != RecyclerView.NO_POSITION) {
            getItem(position)
        } else {
            null
        }
    }

    inner class ViewHolder<out V : ViewDataBinding>(val binding: V) :
        LifecycleViewHolder(binding.root), LifecycleOwner {
        init {
            binding.lifecycleOwner = this
            binding.setVariable(lifecycleVariableId, LifecycleOwnerWrapper(this))
        }

        override fun onAttach() {
            super.onAttach()
            binding.invalidateAll()
        }

        fun setVariable(item: MLI) {
            binding.setVariable(viewModelVariableId, item)
        }

        fun bind(item: MLI) {
            binding.apply {
                root.bindViewModel(item, LifecycleOwnerWrapper(this@ViewHolder))
                executePendingBindings()
            }
        }

        fun unbind() {
            binding.unbind()
        }
    }
}
