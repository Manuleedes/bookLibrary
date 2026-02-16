package com.lidigu.core.domain

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.FileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToURL

class IOSFileSaver : FileSaver {
    override suspend fun saveFile(fileName: String, bytes: ByteArray): String {
        val fileManager = FileManager.defaultManager
        val documentDirectory = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as platform.Foundation.NSURL
        val fileURL = documentDirectory.URLByAppendingPathComponent(fileName)!!
        
        val data = bytes.usePinned { pinned ->
            NSData.dataWithBytes(pinned.addressOf(0), bytes.size.toULong())
        }
        data.writeToURL(fileURL, true)
        return fileURL.path!!
    }

    override fun getSavedFilePath(fileName: String): String? {
        val fileManager = FileManager.defaultManager
        val documentDirectory = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as platform.Foundation.NSURL
        val fileURL = documentDirectory.URLByAppendingPathComponent(fileName)!!
        return if (fileManager.fileExistsAtPath(fileURL.path!!)) fileURL.path else null
    }

    override fun deleteFile(fileName: String) {
        val fileManager = FileManager.defaultManager
        val documentDirectory = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask).first() as platform.Foundation.NSURL
        val fileURL = documentDirectory.URLByAppendingPathComponent(fileName)!!
        if (fileManager.fileExistsAtPath(fileURL.path!!)) {
            fileManager.removeItemAtURL(fileURL, null)
        }
    }

    override fun isFileSaved(fileName: String): Boolean {
        return getSavedFilePath(fileName) != null
    }
}
