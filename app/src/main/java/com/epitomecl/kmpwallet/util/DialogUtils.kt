package com.epitomecl.kmpwallet.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object DialogUtils {

    fun setAlertDialog(context: Context?, msg: String) {
        AlertDialog.Builder(context).setTitle("알림").setMessage(msg)
                .setPositiveButton("확인") { dialog, which ->
                    //
                }.show()
    }
}