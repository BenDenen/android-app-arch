package com.kvazars.arch.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kvazars.arch.sample.scenarios.background.BackgroundProcessingScenarioFragment
import com.kvazars.arch.sample.scenarios.lifecycle.ComponentsLifecycleScenarioFragment
import com.kvazars.arch.sample.scenarios.permissions.RuntimePermissionsScenarioFragment
import com.kvazars.arch.sample.scenarios.widgets.edittext.EditTextScenarioFragment
import com.kvazars.arch.sample.scenarios.widgets.recyclerview.SimpleRecyclerViewScenarioFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        components_lifecycle_btn.setOnClickListener {
            ComponentsLifecycleScenarioFragment().launch()
        }

        edit_text_widget_btn.setOnClickListener {
            EditTextScenarioFragment().launch()
        }

        simple_recycler_view_widget_btn.setOnClickListener {
            SimpleRecyclerViewScenarioFragment().launch()
        }

        background_processing_btn.setOnClickListener {
            BackgroundProcessingScenarioFragment().launch()
        }

        runtime_permissions_btn.setOnClickListener {
            RuntimePermissionsScenarioFragment().launch()
        }
    }

    private fun Fragment.launch() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, this)
            .addToBackStack(null)
            .commit()
    }

}
