package ru.anbazhan.library.base

import android.os.Bundle
import androidx.lifecycle.*

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver  {

    open fun reInit(args: Bundle?) {
    }
}