package ru.anbazhan.library.recycler

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView

abstract class LiveDataBindingRVAdapter<VH : LiveDataBindingRVAdapter.ViewHolder> :
    RecyclerView.Adapter<VH>() {

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        holder.markDetach()
        super.onViewDetachedFromWindow(holder)
    }

    open class ViewHolder(itemView: View) :
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
