package com.stori.interviewtest.ui

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText

const val TAG = "AppTag"
object Utils {
    fun printLog(e: Exception) = Log.e(TAG, e.stackTraceToString())

    fun showMessage(
        context: Context,
        message: String?
    ) = makeText(context, message, LENGTH_LONG).show()
}
