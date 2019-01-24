package com.kvazars.arch.sample.scenarios.widgets.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kvazars.arch.controls.check.bind
import com.kvazars.arch.controls.list.recyclerview.bind
import com.kvazars.arch.core.base.LibFragment
import com.kvazars.arch.core.setBindings
import com.kvazars.arch.sample.R
import kotlinx.android.synthetic.main.fragment_simple_recycler_view_scenario.*
import kotlinx.android.synthetic.main.item_simple_recycler_view.*

class SimpleRecyclerViewScenarioFragment : LibFragment<SimpleRecyclerViewScenarioViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple_recycler_view_scenario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBindings {
//            bind(vm.items, recycler_view, R.layout.item_simple_recycler_view) { item, _ ->
//                item_label_text.text = item
//            }
            bind(vm.editableItems, recycler_view, R.layout.item_simple_recycler_view) { item, _ ->
                itemView.setBindings {
                    bind(item.check, item_label_text)
                }
            }
        }
    }

    override fun createViewModel(): SimpleRecyclerViewScenarioViewModel {
        return SimpleRecyclerViewScenarioViewModel()
    }

}
