package com.kvazars.arch.sample.scenarios.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.kvazars.arch.core.base.LibFragment
import com.kvazars.arch.core.setBindings
import com.kvazars.arch.sample.R
import kotlinx.android.synthetic.main.fragment_components_lifecycle_scenario.*

class ComponentsLifecycleScenarioFragment : LibFragment<ComponentsLifecycleScenarioViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.newLifecycleEventAction.fire("Fragment: onCreate")
    }

    override fun onDestroy() {
        vm.newLifecycleEventAction.fire("Fragment: onDestroy")
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm.newLifecycleEventAction.fire("Fragment: onCreateView")
        return inflater.inflate(R.layout.fragment_components_lifecycle_scenario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.newLifecycleEventAction.fire("Fragment: onViewCreated")

        view.setBindings {
            bind(vm.logs) {
                logs_txt.text = it
                scroll_container.post {
                    scroll_container.fullScroll(ScrollView.FOCUS_DOWN)
                }
            }
        }
    }

    override fun onDestroyView() {
        vm.newLifecycleEventAction.fire("Fragment: onDestroyView")
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        vm.newLifecycleEventAction.fire("Fragment: onStart")
    }

    override fun onStop() {
        vm.newLifecycleEventAction.fire("Fragment: onStop")
        super.onStop()
    }

    override fun createViewModel(): ComponentsLifecycleScenarioViewModel {
        return ComponentsLifecycleScenarioViewModel()
    }

}
