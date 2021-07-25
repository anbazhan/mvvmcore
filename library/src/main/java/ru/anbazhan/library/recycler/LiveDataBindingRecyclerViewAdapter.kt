package ru.anbazhan.library.recycler

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

abstract class LiveDataBindingRecyclerViewAdapter<T : LiveDataBindingRecyclerViewAdapter.LiveDataBindingViewHolder> :
    RecyclerView.Adapter<T>() {

    override fun onViewAttachedToWindow(holder: T) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: T) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }

    open class LiveDataBindingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), LifecycleOwner {

        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        fun markAttach() {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun markDetach() {
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }
}
