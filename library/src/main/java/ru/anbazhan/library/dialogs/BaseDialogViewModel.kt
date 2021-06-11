package ru.anbazhan.library.dialogs

import ru.anbazhan.library.base.BaseViewModel

open class BaseDialogViewModel : BaseViewModel() {

    var closeAction: (() -> Unit)? = null

    open fun close() {
        closeAction?.invoke()
    }
}