package com.lidigu.book.data.download

import com.lidigu.book.domain.DownloadManager
import com.lidigu.book.domain.DownloadState
import com.lidigu.core.domain.FileSaver
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.readRemaining
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class KtorDownloadManager(
    private val httpClient: HttpClient,
    private val fileSaver: FileSaver
): DownloadManager {

    override fun downloadBook(url: String, fileName: String): Flow<DownloadState> = flow {
        emit(DownloadState.Downloading(0f))
        try {
            val response = httpClient.get(url)
            val channel: ByteReadChannel = response.bodyAsChannel()
            val totalBytes = response.headers["Content-Length"]?.toLong() ?: 0L
            val byteArray = response.body<ByteArray>()
            val path = fileSaver.saveFile(fileName, byteArray)
            emit(DownloadState.Finished(path))
            
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DownloadState.Failed(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteBook(fileName: String) {
        withContext(Dispatchers.IO) {
            fileSaver.deleteFile(fileName)
        }
    }

    override fun isBookDownloaded(fileName: String): Boolean {
        return fileSaver.isFileSaved(fileName)
    }

    override fun getDownloadedFilePath(fileName: String): String? {
        return fileSaver.getSavedFilePath(fileName)
    }
}
