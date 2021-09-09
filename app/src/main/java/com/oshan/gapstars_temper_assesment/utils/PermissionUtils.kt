package com.oshan.gapstars_temper_assesment.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object PermissionUtils {

    /**
     * show permission rationale
     */
    fun showPermissionExplanation(
        context: Context,
        title: String,
        message: String,
        onUserAgreeToGrantPermission: () -> Unit
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, id ->
                    onUserAgreeToGrantPermission()

                })
        builder.create().show()
    }

}