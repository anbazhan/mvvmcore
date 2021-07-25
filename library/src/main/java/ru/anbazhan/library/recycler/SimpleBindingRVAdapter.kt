package ru.anbazhan.library.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class SimpleBindingRVAdapter<T>(
    @LayoutRes private val holderLayout: Int,
    private val variableId: Int,
    private val onItemClickListener: ((item: T, position: Int) -> Unit)? = null
) : BaseBindingRVAdapter<T, SimpleBindingRVAdapter.SimpleBindingViewHolder<T>>() {

    override fun createViewHolderInstance(
        parent: ViewGroup,
        viewType: Int
    ): SimpleBindingViewHolder<T> {
        return SimpleBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                holderLayout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SimpleBindingViewHolder<T>, position: Int) {
        val item = items[position]
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(item, position)
        }
        holder.binding.setVariable(variableId, item)
    }

    open class SimpleBindingViewHolder<T>(
        binding: ViewDataBinding
    ) : BaseBindingViewHolder<T>(binding)
}
