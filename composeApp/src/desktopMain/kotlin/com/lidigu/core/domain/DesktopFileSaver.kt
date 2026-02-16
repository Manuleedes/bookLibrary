package com.lidigu.core.domain

import java.io.File

class DesktopFileSaver: FileSaver {

    private val appDir = File(System.getProperty("user.home"), ".bookpedia").apply {
        if(!exists()) mkdirs()
    }

    override suspend fun saveFile(fileName: String, bytes: ByteArray): String {
        val file = File(appDir, fileName)
        file.writeBytes(bytes)
        return file.absolutePath
    }

    override fun getSavedFilePath(fileName: String): String? {
        val file = File(appDir, fileName)
        return if(file.exists()) file.absolutePath else null
    }

    override fun deleteFile(fileName: String) {
        val file = File(appDir, fileName)
        if(file.exists()) {
            file.delete()
        }
    }

    override fun isFileSaved(fileName: String): Boolean {
        val file = File(appDir, fileName)
        return file.exists()
    }
}
