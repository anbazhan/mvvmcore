package ru.anbazhan.library.recycler

import android.view.View

open class BindingItem<T>(
    val brId: Int,
    val layoutResource: Int,
    val item: T
) {
    fun bind(view: View) {}
}