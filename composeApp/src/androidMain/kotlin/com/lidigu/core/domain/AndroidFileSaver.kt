package com.lidigu.core.domain

import android.content.Context
import java.io.File

class AndroidFileSaver(
    private val context: Context
): FileSaver {

    override suspend fun saveFile(fileName: String, bytes: ByteArray): String {
        val file = File(context.filesDir, fileName)
        file.writeBytes(bytes)
        return file.absolutePath
    }

    override fun getSavedFilePath(fileName: String): String? {
        val file = File(context.filesDir, fileName)
        return if(file.exists()) file.absolutePath else null
    }

    override fun deleteFile(fileName: String) {
        val file = File(context.filesDir, fileName)
        if(file.exists()) {
            file.delete()
        }
    }

    override fun isFileSaved(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }
}
