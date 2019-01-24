@file:Suppress("unused")

package com.kvazars.arch.controls.list

import com.kvazars.arch.core.LibViewModel

class ItemsListControl<T>(vm: LibViewModel, initialItems: List<T>) {

    val data = vm.State(initialItems)

}

fun <T> LibViewModel.itemsListControl(initialItems: List<T>): ItemsListControl<T> {
    return ItemsListControl(this, initialItems)
}
