package ru.anbazhan.library.recycler

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:items")
fun <E> RecyclerView.bindItems(items: MutableList<BindingItem<E>>?) {
    items?.let {
        @Suppress("UNCHECKED_CAST")
        try {
            if (adapter != null && adapter is BaseRVAdapter<*, *>) {
                (adapter as BaseRVAdapter<E, BindingItem<E>>).updateItems(
                    items
                )
            }
        } catch (t: Throwable) {
            throw IllegalStateException("Incoming adapter is an incompatible type")
        }
    }
}
