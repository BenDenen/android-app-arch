@file:Suppress("unused")

package com.kvazars.arch.controls.permissions

import com.kvazars.arch.core.ViewModelBinder
import com.kvazars.arch.core.base.LibFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlin.math.absoluteValue

fun ViewModelBinder.bind(runtimePermissionControl: RuntimePermissionControl, fragment: LibFragment<*>) {
    val permissions = runtimePermissionControl.permissions
    val requestCode = permissions.contentHashCode().absoluteValue % (2 shl 15)

    fragment.viewDelegate.permissionResults
        .filter { it.requestCode == requestCode }
        .map { permissionsResult ->
            if (permissionsResult.grantResults.find { it == android.content.pm.PackageManager.PERMISSION_DENIED } == null) {
                RuntimePermissionControl.PermissionsResult.Granted
            } else {
                RuntimePermissionControl.PermissionsResult.Denied
            }
        }
        .subscribe {
            runtimePermissionControl.sendResult(it)
        }
        .untilUnbind()

    runtimePermissionControl.requestCommand.observable
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            val allPermissionsGranted = permissions.find { permission ->
                androidx.core.content.ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    permission
                ) == android.content.pm.PackageManager.PERMISSION_DENIED
            } == null

            if (allPermissionsGranted) {
                runtimePermissionControl.sendResult(RuntimePermissionControl.PermissionsResult.Granted)
            } else {
                fragment.requestPermissions(permissions, requestCode)
            }
        }
        .untilUnbind()
}

