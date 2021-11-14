package ru.anbazhan.library.recycler

open class BindingItem<T>(
    val brId: Int,
    val layoutResource: Int,
    val item: T
)