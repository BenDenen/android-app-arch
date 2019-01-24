package com.kvazars.arch.core

import com.jakewharton.rxrelay2.Relay

interface WidgetControl {

    val <T> LibViewModel.State<T>.relay: Relay<T>
        get() = relay

    val <T> LibViewModel.Command<T>.relay: Relay<T>
        get() = relay

    val <T> LibViewModel.Action<T>.relay: Relay<T>
        get() = relay

}
