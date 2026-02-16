package com.lidigu.core.domain

interface FileSaver {
    suspend fun saveFile(fileName: String, bytes: ByteArray): String
    fun getSavedFilePath(fileName: String): String?
    fun deleteFile(fileName: String)
    fun isFileSaved(fileName: String): Boolean
}
