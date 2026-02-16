package com.lidigu.book.domain

import com.lidigu.core.domain.DataError
import com.lidigu.core.domain.EmptyResult
import com.lidigu.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDetails(bookId: String): Result<Book, DataError>

    fun getDownloadedBooks(): Flow<List<Book>>
    suspend fun markAsDownloaded(book: Book, localPath: String): EmptyResult<DataError.Local>
    suspend fun deleteDownload(id: String)
}