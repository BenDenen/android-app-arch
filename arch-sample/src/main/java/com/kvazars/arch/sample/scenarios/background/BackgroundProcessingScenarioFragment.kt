package com.kvazars.arch.sample.scenarios.background

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.kvazars.arch.core.base.LibFragment
import com.kvazars.arch.core.setBindings
import com.kvazars.arch.sample.R
import kotlinx.android.synthetic.main.fragment_background_processing_scenario.*

class BackgroundProcessingScenarioFragment : LibFragment<BackgroundProcessingScenarioViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_background_processing_scenario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBindings {
            bind(calculate_btn.clicks(), vm.calculateAction)

            bind(vm.inProgress) {
                work_result_txt.text = if (it) "In progress..." else ""
            }

            bind(vm.primeIndex) {
                prime_number_txt.text = "${it}th prime number"
            }

            bind(vm.primeNumber) {
                it.toNullable()?.let {
                    work_result_txt.text = it.toString()
                }
            }
        }
    }

    override fun createViewModel(): BackgroundProcessingScenarioViewModel {
        return BackgroundProcessingScenarioViewModel()
    }
}
