package com.kvazars.arch.sample.scenarios.widgets.edittext

import android.util.Patterns
import com.kvazars.arch.controls.textinput.textInputControl
import com.kvazars.arch.core.LibViewModel

class EditTextScenarioViewModel : LibViewModel() {

    val simpleTextInput = textInputControl("")
    val formattedTextInput = textInputControl("", { it.filter { it.isDigit() } })
    val materialTextInput = textInputControl("", hideErrorOnUserInput = false)

    init {
        materialTextInput.text.listenUntilDestroy {
            if (it.isNotBlank()) {
                if (Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
                    materialTextInput.error.set("")
                } else {
                    materialTextInput.error.set("Invalid email")
                }
            }
        }
    }
}
