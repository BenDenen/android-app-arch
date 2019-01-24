@file:Suppress("unused")

package com.kvazars.arch.controls.list.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kvazars.arch.core.LibViewModel
import com.kvazars.arch.core.ViewModelBinder
import com.kvazars.arch.core.setBindings

fun <T> ViewModelBinder.bind(
    items: LibViewModel.State<List<T>>,
    recyclerView: RecyclerView,
    layoutId: Int,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false),
    binder: SimpleViewHolder.(item: T, position: Int) -> Unit
) {
    val adapter = PluggableRecyclerViewAdapter<T, SimpleViewHolder>(
        { layoutInflater, parent, _ ->
            SimpleViewHolder(layoutInflater.inflate(layoutId, parent, false))
        },
        { viewHolder, item, position -> viewHolder.binder(item, position) }
    )
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = adapter

    recyclerView.setBindings {
        bind(items) {
            adapter.submitList(it)
        }
    }
}

class RecyclerViewBinderConfig<T>(
    val recyclerView: RecyclerView,
    val adapter: PluggableRecyclerViewAdapter<T, SimpleViewHolder>,
    val layoutManager: RecyclerView.LayoutManager
) {

    class Builder<T>(
        private val parentViewModelBinder: ViewModelBinder,
        private val recyclerView: RecyclerView
    ) {
        private var viewBuilder: ((
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ) -> SimpleViewHolder)? = null
        private var viewBinder: (SimpleViewHolder.(item: T, position: Int) -> Unit)? = null
        private var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            recyclerView.context,
            RecyclerView.VERTICAL,
            false
        )

        fun view(@LayoutRes layoutId: Int, onViewHolderCreated: (SimpleViewHolder.() -> Unit)? = null): Builder<T> {
            viewBuilder = { layoutInflater, parent, _ ->
                SimpleViewHolder(layoutInflater.inflate(layoutId, parent, false)).apply {
                    onViewHolderCreated?.invoke(this)
                }
            }
            return this
        }

        fun view(
            builder: (layoutInflater: LayoutInflater, parent: ViewGroup, viewType: Int) -> View,
            onViewHolderCreated: (SimpleViewHolder.() -> Unit)? = null
        ): Builder<T> {
            viewBuilder = { layoutInflater, parent, viewType ->
                SimpleViewHolder(builder(layoutInflater, parent, viewType)).apply {
                    onViewHolderCreated?.invoke(this)
                }
            }
            return this
        }

        fun bind(binder: SimpleViewHolder.(item: T, position: Int) -> Unit): Builder<T> {
            viewBinder = binder
            return this
        }

        fun layoutManager(layoutManager: RecyclerView.LayoutManager): Builder<T> {
            this.layoutManager = layoutManager
            return this
        }

        fun build(): RecyclerViewBinderConfig<T> {
            if (viewBuilder == null) throw IllegalStateException("Please use a Builder#view method")
            if (viewBinder == null) throw IllegalStateException("Please use a Builder#bind method")

            return RecyclerViewBinderConfig(
                recyclerView,
                PluggableRecyclerViewAdapter(viewBuilder!!, viewBinder!!),
                layoutManager
            )
        }
    }
}
