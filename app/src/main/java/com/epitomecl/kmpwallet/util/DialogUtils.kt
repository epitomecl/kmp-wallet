package com.epitomecl.kmpwallet.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.epitomecl.kmpwallet.R

object DialogUtils {

    fun setAlertDialog(context: Context?, msg: String) {
        AlertDialog.Builder(context).setTitle(context?.getString(R.string.alert)).setMessage(msg)
                .setPositiveButton(context?.getString(R.string.alert_ok)) { dialog, which ->
                    //
                }.show()
    }

    fun setAlertDialog(context: Context?, msg: String, listener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).setTitle(context?.getString(R.string.alert)).setMessage(msg)
                .setPositiveButton(context?.getString(R.string.alert_ok)) { dialog, which ->
                    listener.onClick(null, 0)
                }.show()
    }

    fun setConfirmDialog(context: Context?, msg: String, listener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context).setTitle(context?.getString(R.string.alert)).setMessage(msg)
                .setNegativeButton(context?.getString(R.string.alert_cancel)) { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton(context?.getString(R.string.alert_ok)) { dialog, which ->
                    listener.onClick(null, 0)
                }.show()
    }
}
