package ru.anbazhan.library.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseRVAdapter<T, B : BindingItem<T>>(
    var items: MutableList<B> = mutableListOf()
) : LiveDataBindingRVAdapter<BaseRVAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutResource
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        ).apply { binding.lifecycleOwner = this }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let {
            holder.binding.setVariable(it.brId, it.item)
            it.bind(holder.itemView)
        }
    }

    fun getItem(position: Int): B {
        return items[position]
    }

    open fun updateItems(items: MutableList<B>) {
        this.items = items
        notifyDataSetChanged()
    }

    open class ViewHolder(val binding: ViewDataBinding) :
        LiveDataBindingRVAdapter.ViewHolder(binding.root)
}