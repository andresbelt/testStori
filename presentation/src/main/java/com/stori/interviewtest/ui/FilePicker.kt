package com.stori.interviewtest.ui

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.text.format.DateFormat
import androidx.core.content.FileProvider
import java.io.File
import java.util.Date

class FilePicker(
    val context: Context,
    private val authority: String
) {
    fun createTempFile(): File {
        val fileName = DateFormat.format("ddMMyyy_hhmmss", Date()).toString()
        return File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$fileName.jpg"
        )
    }

    fun uriFromFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context, authority, file
        )
    }
}
