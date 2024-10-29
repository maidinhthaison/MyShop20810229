package com.ql2.myshop.utils

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AppDialog {
    private fun getDialogBuilder(context: Context): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(context)
    }

    fun displayErrorMessage(context: Context, title: Int, message: Int,
                            positive: Int,
                            positiveClicked: DialogInterface.OnClickListener) {
        val materialAlertDialogBuilder = getDialogBuilder(context)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setMessage(message)
        materialAlertDialogBuilder.setCancelable(false)
        materialAlertDialogBuilder.setPositiveButton(positive, positiveClicked)
        materialAlertDialogBuilder.show()
    }

    fun displayConfirmDialog(
        context: Context,
        title: Int,
        message: Int,
        positive: Int,
        negative: Int,
        positiveClicked: DialogInterface.OnClickListener
    ) {
        val materialAlertDialogBuilder = getDialogBuilder(context)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setMessage(message)
        materialAlertDialogBuilder.setPositiveButton(positive, positiveClicked)
        materialAlertDialogBuilder.setNegativeButton(negative, null)
        materialAlertDialogBuilder.show()
    }
}