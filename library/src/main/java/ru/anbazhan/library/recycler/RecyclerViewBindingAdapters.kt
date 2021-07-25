package ru.anbazhan.library.recycler

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("android:items")
fun <T> RecyclerView.bindItems(items: MutableList<T>?) {
    items?.let {
        @Suppress("UNCHECKED_CAST")
        try {
            if (adapter != null && adapter is BaseBindingRVAdapter<*, *>) {
                (adapter as BaseBindingRVAdapter<T, *>).updateItems(
                    items
                )
            }
        } catch (t: Throwable) {
            throw IllegalStateException("Incoming adapter is an incompatible type")
        }
    }
}
