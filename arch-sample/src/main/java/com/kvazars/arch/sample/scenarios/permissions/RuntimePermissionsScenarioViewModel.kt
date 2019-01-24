package com.kvazars.arch.sample.scenarios.permissions

import android.Manifest
import com.kvazars.arch.controls.dialog.dialogControl
import com.kvazars.arch.controls.permissions.RuntimePermissionControl
import com.kvazars.arch.controls.permissions.runtimePermissionsControl
import com.kvazars.arch.core.LibViewModel

class RuntimePermissionsScenarioViewModel : LibViewModel() {

    val coarseLocationRuntimePermission = runtimePermissionsControl(Manifest.permission.ACCESS_COARSE_LOCATION)
    val accountsRuntimePermission = runtimePermissionsControl(Manifest.permission.GET_ACCOUNTS)
    val multipleRuntimePermissions = runtimePermissionsControl(
        Manifest.permission.GET_ACCOUNTS,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val command = Command<String>()

    val permissionResultAlert = dialogControl<String, Unit>()

    val requestLocationPermissionAction = Action<Unit>()
    val requestAccountsPermissionAction = Action<Unit>()
    val requestMultiplePermissionsAction = Action<Unit>()

    init {
        accountsRuntimePermission.request().subscribe().untilDestroy()

        // MVP-Lib style
        requestLocationPermissionAction.listenUntilDestroy {
            coarseLocationRuntimePermission.request().listenUntilDestroy { permissionResult ->
                val alertText = if (permissionResult is RuntimePermissionControl.PermissionsResult.Granted)
                        "Permission granted" else "Permission not granted"
                permissionResultAlert.show(alertText)
            }
        }

        // Rx style
        requestAccountsPermissionAction.observable
            .flatMapSingle { accountsRuntimePermission.request() }
            .subscribe { permissionResult ->
                val alertText = if (permissionResult is RuntimePermissionControl.PermissionsResult.Granted)
                    "Permission granted" else "Permission not granted"
                permissionResultAlert.show(alertText)
            }
            .untilDestroy()


        requestMultiplePermissionsAction.observable
            .flatMapSingle { multipleRuntimePermissions.request() }
            .subscribe { permissionResult ->
                val alertText = if (permissionResult is RuntimePermissionControl.PermissionsResult.Granted)
                    "Permission granted" else "Permission not granted"
                permissionResultAlert.show(alertText)
            }
            .untilDestroy()
    }
}
