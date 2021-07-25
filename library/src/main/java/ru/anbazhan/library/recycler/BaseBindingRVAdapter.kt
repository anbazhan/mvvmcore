package ru.anbazhan.library.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingRVAdapter<Model, VH : BaseBindingRVAdapter.BaseBindingViewHolder<Model>>(
    var items: MutableList<Model> = mutableListOf()
) : LiveDataBindingRecyclerViewAdapter<VH>() {

    abstract fun createViewHolderInstance(parent: ViewGroup, viewType: Int): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolderInstance(parent, viewType).apply {
            binding.lifecycleOwner = this
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    fun getItem(position: Int): Model {
        return items[position]
    }

    fun inflate(parent: ViewGroup, layoutResId: Int): ViewDataBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResId,
            parent,
            false
        )

    open fun updateItems(items: MutableList<Model>) {
        this.items = items
        notifyDataSetChanged()
    }

    open class BaseBindingViewHolder<Model>(val binding: ViewDataBinding) :
        LiveDataBindingViewHolder(binding.root) {

        open fun bind(model: Model, variableId: Int = 0) {}
    }
}
