package com.github.lilzulf.masaya.Util

import android.content.Context
import android.widget.Toast

fun tampilToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
