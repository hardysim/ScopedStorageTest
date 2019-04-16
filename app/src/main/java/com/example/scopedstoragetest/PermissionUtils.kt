package com.example.scopedstoragetest

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

inline fun Activity.checkStoragePermission(requestCode: Int, onChecked: () -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissionsWithError(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            requestCode
        )

        return
    }

    onChecked()
}

fun Activity.requestPermissionsWithError(
    permissions: Array<String>,
    requestCode: Int
) {
    val requestPermissions = {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    if (permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }) {
        // Provide an additional rationale to the user if the permission was not granted
        // and the user would benefit from additional context for the use of the permission.
        // Display a SnackBar with a button to request the missing permission.

        // TODO show error

    } else {
        // Request the permission. The result will be received in onRequestPermissionResult().
        requestPermissions.invoke()
    }
}

fun verifyPermissions(grantResults: IntArray) =
    grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }
