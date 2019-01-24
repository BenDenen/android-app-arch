package com.kvazars.arch.sample.scenarios.lifecycle

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import com.kvazars.arch.core.LibViewModel
import java.text.SimpleDateFormat
import java.util.*

class ComponentsLifecycleScenarioViewModel : LibViewModel() {

    val logs = State<CharSequence>()
    val newLifecycleEventAction = Action<String>()

    private val logsBuilder = SpannableStringBuilder()
    private val vmEventsColor = 0xFF003c7c.toInt()
    private val viewEventsColor = 0xFFba0056.toInt()
    private val timeFormatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    init {
        appendLifecycleEvent("ViewModel: init", vmEventsColor)

        newLifecycleEventAction.listenUntilDestroy {
            appendLifecycleEvent(it, viewEventsColor)
        }
    }

    override fun onCreate() {
        super.onCreate()
        appendLifecycleEvent("ViewModel: onCreate", vmEventsColor)
    }

    override fun onBind() {
        super.onBind()
        appendLifecycleEvent("ViewModel: onBind", vmEventsColor)
    }

    override fun onUnbind() {
        appendLifecycleEvent("ViewModel: onUnbind", vmEventsColor)
        super.onUnbind()
    }

    private fun appendLifecycleEvent(event: String, color: Int) {
        var start = logsBuilder.length
        logsBuilder.append(timeFormatter.format(Date()))
        logsBuilder.setSpan(
            RelativeSizeSpan(0.5f),
            start,
            logsBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        logsBuilder.append(" ")
        start = logsBuilder.length
        logsBuilder.append(event)
        logsBuilder.setSpan(
            ForegroundColorSpan(color),
            start,
            logsBuilder.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        logsBuilder.append('\n')
        logs.set(logsBuilder)
    }

}
