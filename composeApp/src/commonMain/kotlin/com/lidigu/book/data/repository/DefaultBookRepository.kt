package com.lidigu.book.data.repository

import androidx.sqlite.SQLiteException
import com.lidigu.book.data.database.FavoriteBookDao
import com.lidigu.book.data.mappers.toBook
import com.lidigu.book.data.mappers.toBookEntity
import com.lidigu.book.data.network.RemoteBookDataSource
import com.lidigu.book.domain.Book
import com.lidigu.book.domain.BookRepository
import com.lidigu.core.domain.DataError
import com.lidigu.core.domain.EmptyResult
import com.lidigu.core.domain.Result
import com.lidigu.core.domain.map
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                coroutineScope {
                    dto.books
                        .map { searchedBook ->
                            async { remoteBookDataSource.getBookDetails(searchedBook.id) }
                        }
                        .awaitAll()
                        .mapNotNull { result ->
                            when(result) {
                                is Result.Success -> result.data.toBook()
                                is Result.Error -> null
                            }
                        }
                        .filter { it.downloadUrl != null }
                }
            }
    }

    override suspend fun getBookDetails(bookId: String): Result<Book, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)

        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.toBook() }
        } else {
            Result.Success(localResult.toBook())
        }
    }

    override fun getDownloadedBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities
                    .filter { it.localPath != null }
                    .map { it.toBook() }
            }
    }

    override suspend fun markAsDownloaded(book: Book, localPath: String): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.copy(localPath = localPath).toBookEntity())
            Result.Success(Unit)
        } catch(e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteDownload(id: String) {
        val book = favoriteBookDao.getFavoriteBook(id)
        if (book != null) {
            favoriteBookDao.upsert(book.copy(localPath = null))
        }
    }
}