package com.lidigu.book.domain

import kotlinx.coroutines.flow.Flow

interface DownloadManager {
    fun downloadBook(url: String, fileName: String): Flow<DownloadState>
    suspend fun deleteBook(fileName: String)
    fun isBookDownloaded(fileName: String): Boolean
    fun getDownloadedFilePath(fileName: String): String?
}

sealed interface DownloadState {
    data object Idle : DownloadState
    data class Downloading(val progress: Float) : DownloadState
    data class Finished(val path: String) : DownloadState
    data class Failed(val error: Throwable) : DownloadState
}
