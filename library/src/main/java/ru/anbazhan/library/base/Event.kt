package ru.anbazhan.library.base

class Event<T>(
    private val value: T
) {
    private var wasPerformed: Boolean = false

    fun getValue(): T? =
        if (wasPerformed) {
            null
        } else {
            wasPerformed = true
            value
        }
}