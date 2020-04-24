package com.greatdayhr.support.core.extension

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.widget.Toast

fun Context.isTimeAutomatic(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        Settings.Global.getInt(
            contentResolver,
            Settings.Global.AUTO_TIME,
            0
        ) === 1
    } else {
        Settings.System.getInt(
            contentResolver,
            Settings.System.AUTO_TIME,
            0
        ) == 1
    }
}

fun Context.toast(message: String? = "Message empty") =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.longToast(message: String? = "Message empty") =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()