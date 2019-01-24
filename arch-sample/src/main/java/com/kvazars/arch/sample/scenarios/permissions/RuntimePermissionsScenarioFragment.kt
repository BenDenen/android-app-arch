package com.kvazars.arch.sample.scenarios.permissions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.jakewharton.rxbinding2.view.clicks
import com.kvazars.arch.controls.dialog.DialogControl
import com.kvazars.arch.controls.dialog.bind
import com.kvazars.arch.controls.permissions.bind
import com.kvazars.arch.core.base.LibFragment
import com.kvazars.arch.core.setBindings
import com.kvazars.arch.sample.R
import kotlinx.android.synthetic.main.fragment_runtime_permissions_scenario.*

class RuntimePermissionsScenarioFragment : LibFragment<RuntimePermissionsScenarioViewModel>() {

    private val alertDialogCreator = { data: String, dc: DialogControl<String, Unit> ->
        AlertDialog.Builder(context!!)
            .setMessage(data)
            .setPositiveButton("Ok") { _, _ -> dc.dismiss() }
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_runtime_permissions_scenario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBindings {
            bind(request_coarse_location_permission_btn.clicks(), vm.requestLocationPermissionAction)
            bind(request_accounts_permission_btn.clicks(), vm.requestAccountsPermissionAction)
            bind(request_multiple_permissions_btn.clicks(), vm.requestMultiplePermissionsAction)

            bind(vm.command) {
                println(it)
            }

            val fragment = this@RuntimePermissionsScenarioFragment

//            bind(vm.coarseLocationRuntimePermission, fragment)
            bind(vm.accountsRuntimePermission, fragment)
//            bind(vm.multipleRuntimePermissions, fragment)

            bind(vm.permissionResultAlert, alertDialogCreator)
        }
    }

    override fun createViewModel(): RuntimePermissionsScenarioViewModel {
        return RuntimePermissionsScenarioViewModel()
    }
}
