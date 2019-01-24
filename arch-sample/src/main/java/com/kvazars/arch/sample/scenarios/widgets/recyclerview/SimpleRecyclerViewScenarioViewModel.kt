package com.kvazars.arch.sample.scenarios.widgets.recyclerview

import com.kvazars.arch.controls.check.checkControl
import com.kvazars.arch.core.LibViewModel

class SimpleRecyclerViewScenarioViewModel : LibViewModel() {

    class EditableItem(vm: LibViewModel) {
        val check = vm.checkControl()
    }

    val items = State(IntRange(0, 1000).map {
        "0x${it.toString(16).padStart(3, '0')}"
    }.toList())

    val editableItems = State(IntRange(0, 1000).map {
        EditableItem(this)
    }.toList())

    val itemClickAction = Action<String>()

    init {
        itemClickAction.listenUntilDestroy {
            println("Clicked $it")
        }
    }

}
