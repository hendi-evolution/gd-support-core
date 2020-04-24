package com.greatdayhr.support.core.extension

import android.app.Activity
import android.app.ProgressDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun Activity.progressDialog(message: String): ProgressDialog {
    val dialog = ProgressDialog(this)
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    dialog.setMessage(message)
    dialog.isIndeterminate = true
    dialog.setCanceledOnTouchOutside(false)
    dialog.cancel()
    return dialog
}

fun Activity.progressDialog(title: String, message: String): ProgressDialog {
    val dialog = ProgressDialog(this)
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.isIndeterminate = true
    dialog.setCanceledOnTouchOutside(false)
    dialog.cancel()
    return dialog
}

fun Activity.densityDpi(widthPixels: Int, heightPixels: Int, scaleFactor: Float): Boolean {
    val widthDp = widthPixels / scaleFactor
    val heightDp = heightPixels / scaleFactor
    val smallestWidth = Math.min(widthDp, heightDp)
    return when {
        smallestWidth >= 720 -> return false
        smallestWidth >= 600 -> return false
        else -> true
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}