package com.kvazars.arch.sample.scenarios.widgets.edittext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kvazars.arch.controls.textinput.bind
import com.kvazars.arch.core.base.LibFragment
import com.kvazars.arch.core.setBindings
import com.kvazars.arch.sample.R
import kotlinx.android.synthetic.main.fragment_edit_text_scenario.*

class EditTextScenarioFragment : LibFragment<EditTextScenarioViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_text_scenario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBindings {
            bind(vm.simpleTextInput, simple_edit_text)
            bind(vm.formattedTextInput, filtered_edit_text)
            bind(vm.materialTextInput, error_edit_text)

            bind(vm.simpleTextInput.text) {
                simple_entered_text.text = "Entered text: $it"
            }

            bind(vm.formattedTextInput.text) {
                filtered_entered_text.text = "Entered text: $it"
            }
        }
    }

    override fun createViewModel(): EditTextScenarioViewModel {
        return EditTextScenarioViewModel()
    }
}
